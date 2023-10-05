package com.mel.allmovies.data.entities

data class PaginationMovies(val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int) {
}