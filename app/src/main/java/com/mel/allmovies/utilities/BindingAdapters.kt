package com.mel.allmovies.utilities

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mel.allmovies.data.entities.Movie
import com.mel.allmovies.view.ListMoviesAdapter

@BindingAdapter(value = ["moviesList", "isNewList"], requireAll = false)
fun setMoviesList(recyclerView: RecyclerView,listMovies: List<Movie>, isNewList: Boolean){
    (recyclerView.adapter as? ListMoviesAdapter)?.updateMovies(listMovies, isNewList)
}