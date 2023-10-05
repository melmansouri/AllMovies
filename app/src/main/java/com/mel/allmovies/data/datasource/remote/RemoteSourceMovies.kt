package com.mel.allmovies.data.datasource.remote

import androidx.lifecycle.LiveData
import com.mel.allmovies.data.entities.MovieDetail
import com.mel.allmovies.data.entities.PaginationMovies
import com.mel.allmovies.utilities.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.Callback

class RemoteSourceMovies(private val rtService: RTService): IRemoteSourceMovies {
    override fun getAllMovies(page: Int): Single<PaginationMovies> {
        val queryMap = mapOf("page" to page.toString(),
            "api_key" to Constants.api_key)
        return rtService.getAllMovies(queryMap)
    }

    override fun getMovieDetail(idMovie: Int): Single<MovieDetail> {
        return rtService.getMovieDetails(idMovie)
    }
}