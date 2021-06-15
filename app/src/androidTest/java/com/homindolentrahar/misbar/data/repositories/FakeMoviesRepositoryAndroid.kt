package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.domain.models.MoviesModel
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class FakeMoviesRepositoryAndroid : MoviesRepository {

    private val dummyMoviesModel = DummyData.generateMoviesModel()
    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()

    override fun getNowPlaying(): Observable<List<MoviesModel>> {
        return Observable.just(dummyMoviesModel)
    }

    override fun getPopularMovies(): Observable<PagingData<MoviesModel>> {
        return Observable.just(PagingData.from(dummyMoviesModel))
    }

    override fun getMoviesByGenre(genreId: Int): Observable<PagingData<MoviesModel>> {
        return Observable.just(PagingData.from(dummyMoviesModel))
    }

    override fun getMoviesDetail(moviesId: Int): Single<DetailMoviesModel> {
        return Single.just(dummyDetailMoviesModel)
    }

}