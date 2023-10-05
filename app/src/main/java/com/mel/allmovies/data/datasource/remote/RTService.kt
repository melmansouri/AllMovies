package com.mel.allmovies.data.datasource.remote

import com.mel.allmovies.utilities.Constants
import com.mel.allmovies.data.entities.MovieDetail
import com.mel.allmovies.data.entities.PaginationMovies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Retrofit Service
 */
interface RTService {
    @GET("discover/movie")
    fun getAllMovies(@QueryMap map: Map<String, String>): Single<PaginationMovies>

    @GET("movie/{id}?api_key=${Constants.api_key}")
    fun getMovieDetails(@Path("id") idMovie: Int): Single<MovieDetail>
}