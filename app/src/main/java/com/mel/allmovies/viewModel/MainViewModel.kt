package com.mel.allmovies.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mel.allmovies.data.entities.Movie
import com.mel.allmovies.repository.listmovies.IMoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val moviesRepository: IMoviesRepository): AppViewModel() {
    private val _listMovies = MutableLiveData<List<Movie>>(emptyList())
    val listMovies: LiveData<List<Movie>> = _listMovies
    private val startPage = 1
    private var nextPage: Int = startPage
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    private var totalPages: Int = 0
    private val _isNewList = MutableLiveData(false)
    val isMoviesListNew: LiveData<Boolean> = _isNewList
    init {
        _isLoading.value = true
        _isNewList.value = true
        loadMovies()
    }
    private fun loadMovies(isRefresh: Boolean = false){
        disposable.add(moviesRepository.getAllMovies(nextPage).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result ->
                _isLoading.value = false
                if (result.results.isNotEmpty()){
                    nextPage = result.page + 1
                    totalPages = result.total_pages
                    lateinit var listMovies: MutableList<Movie>
                    if (isRefresh){
                        listMovies = result.results.toMutableList()
                    } else{
                        val currentList = (_listMovies.value ?: mutableListOf())
                        listMovies = currentList.toMutableList()
                        listMovies.addAll(result.results)
                    }
                    _listMovies.value = listMovies
                }
            }, {errorResult ->
                _isLoading.value = false
                errorResult.printStackTrace()
                Log.d("listViewModel", "${errorResult.message}")
            }))
    }

    fun loadNextPage(lastItemPositionVisible: Int){
        val sizeList = _listMovies.value?.size ?: 0
        if (nextPage > totalPages) return
        if (lastItemPositionVisible == sizeList - 1 && !getIsLoading()){
            _isNewList.value = false
            loadMovies()
        }
    }

    fun onRefreshMovies(){
        nextPage = startPage
        _isNewList.value = true
        if (!getIsLoading()) loadMovies(true)
    }

    private fun getIsLoading(): Boolean{
        return _isLoading.value ?: false
    }
}