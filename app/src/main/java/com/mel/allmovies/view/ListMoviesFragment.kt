package com.mel.allmovies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mel.allmovies.databinding.FragmentListMoviesBinding
import com.mel.allmovies.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMoviesFragment: Fragment() {
    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private var binding: FragmentListMoviesBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListMoviesBinding.inflate(inflater)
        binding?.lifecycleOwner = this
        binding?.listMoviesViewModel = viewModel
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvMovies?.adapter = ListMoviesAdapter(mutableListOf())
        binding?.rvMovies?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>=0){
                    val gridLayoutManager = (recyclerView.layoutManager as? GridLayoutManager)
                    val lastItemPositionVisible = gridLayoutManager?.findLastCompletelyVisibleItemPosition() ?: -1
                    viewModel.loadNextPage(lastItemPositionVisible)
                }
            }
        })
    }

    companion object {
        val TAG: String? = ListMoviesFragment::class.simpleName
    }
}