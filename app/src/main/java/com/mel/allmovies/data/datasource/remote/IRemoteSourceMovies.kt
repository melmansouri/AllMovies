package com.mel.allmovies.data.datasource.remote

import androidx.lifecycle.LiveData
import com.mel.allmovies.data.entities.MovieDetail
import com.mel.allmovies.data.entities.PaginationMovies
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface IRemoteSourceMovies {
    fun getAllMovies(page: Int): Single<PaginationMovies>
    fun getMovieDetail(idMovie: Int): Single<MovieDetail>
}