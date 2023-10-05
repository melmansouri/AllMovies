package com.mel.allmovies.repository.listmovies

import com.mel.allmovies.data.entities.Movie
import com.mel.allmovies.data.entities.MovieDetail
import com.mel.allmovies.data.entities.PaginationMovies
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface IMoviesRepository {
    fun getAllMovies(page: Int): Single<PaginationMovies>
    fun getMovieDetails(idMovie: Int): Single<MovieDetail>
}