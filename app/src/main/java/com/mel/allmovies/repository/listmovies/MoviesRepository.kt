package com.mel.allmovies.repository.listmovies

import com.mel.allmovies.data.datasource.remote.IRemoteSourceMovies
import com.mel.allmovies.data.datasource.remote.RTService
import com.mel.allmovies.data.entities.MovieDetail
import com.mel.allmovies.data.entities.PaginationMovies
import io.reactivex.rxjava3.core.Single

class MoviesRepository(private val remoteSourceMovie: IRemoteSourceMovies): IMoviesRepository {
    override fun getAllMovies(page: Int): Single<PaginationMovies> {
        return remoteSourceMovie.getAllMovies(page)
    }

    override fun getMovieDetails(idMovie: Int): Single<MovieDetail> {
        return remoteSourceMovie.getMovieDetail(idMovie)
    }
}