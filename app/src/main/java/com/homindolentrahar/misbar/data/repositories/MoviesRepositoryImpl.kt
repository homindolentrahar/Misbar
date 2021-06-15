package com.homindolentrahar.misbar.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.homindolentrahar.misbar.data.sources.remote.MoviesRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.MoviesRxPagingSource
import com.homindolentrahar.misbar.data.sources.remote.dtos.*
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.domain.models.MoviesModel
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MoviesRepositoryImpl(
    private val remote: MoviesRemoteDataSource,
    private val pagingConfig: PagingConfig
) : MoviesRepository {

    override fun getNowPlaying(): Observable<List<MoviesModel>> {
        return getMoviesModelWithGenres(remote.getNowPlaying())
    }

    override fun getPopularMovies(): Observable<PagingData<MoviesModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviesRxPagingSource(remote) }
        ).observable
    }

    override fun getMoviesByGenre(genreId: Int): Observable<PagingData<MoviesModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { MoviesRxPagingSource(remote, genreId) }
        ).observable
    }

    override fun getMoviesDetail(moviesId: Int): Single<DetailMoviesModel> {
        return remote.getMoviesDetail(moviesId)
            .map { response -> response.toModel() }
    }

    private fun getMoviesModelWithGenres(single: Single<MoviesDtoResponse>): Observable<List<MoviesModel>> {
        return Observable.zip(
            single.toObservable(),
            remote.getMoviesGenres().toObservable(),
            { dtoResponse, genresDtoResponse ->
                dtoResponse.results?.map { moviesDto ->
                    moviesDto.toModel().apply {
                        this.genres = genresDtoResponse.genres?.filter { genresDto ->
                            moviesDto.genreIds!!.contains(genresDto.id)
                        }?.take(3)?.toModels() as List<GenresModel>
                    }
                }
            }
        )
    }
}