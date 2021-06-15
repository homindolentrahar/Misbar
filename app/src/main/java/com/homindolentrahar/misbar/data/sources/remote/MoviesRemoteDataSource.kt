package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.data.sources.remote.dtos.DetailMoviesDto
import com.homindolentrahar.misbar.data.sources.remote.dtos.GenresDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.MoviesDtoResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MoviesRemoteDataSource {
    fun getNowPlaying(): Single<MoviesDtoResponse>

    fun getPopularMovies(page: Int): Single<MoviesDtoResponse>

    fun getMoviesByGenre(page: Int, genreId: Int): Single<MoviesDtoResponse>

    fun getMoviesDetail(moviesId: Int): Single<DetailMoviesDto>

    fun getMoviesGenres(): Single<GenresDtoResponse>
}

class RetrofitMoviesRemoteDataSource(private val tmdbApi: TMDBApi) : MoviesRemoteDataSource {

    override fun getNowPlaying(): Single<MoviesDtoResponse> {
        return tmdbApi.getMoviesNowPlaying()
    }

    override fun getPopularMovies(page: Int): Single<MoviesDtoResponse> {
        return tmdbApi.getMoviesPopular(page)
    }

    override fun getMoviesByGenre(page: Int, genreId: Int): Single<MoviesDtoResponse> {
        return tmdbApi.getMoviesByGenre(page, genreId)
    }

    override fun getMoviesDetail(moviesId: Int): Single<DetailMoviesDto> {
        return tmdbApi.getMoviesDetail(id = moviesId)
    }

    override fun getMoviesGenres(): Single<GenresDtoResponse> {
        return tmdbApi.getMoviesGenres()
    }
}