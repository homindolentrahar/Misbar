package com.homindolentrahar.misbar.domain.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.domain.models.MoviesModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MoviesRepository {
    fun getNowPlaying(): Observable<List<MoviesModel>>

    fun getPopularMovies(): Observable<PagingData<MoviesModel>>

    fun getMoviesByGenre(genreId: Int): Observable<PagingData<MoviesModel>>

    fun getMoviesDetail(moviesId: Int): Single<DetailMoviesModel>
}