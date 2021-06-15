package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.homindolentrahar.misbar.data.sources.remote.dtos.MoviesDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.domain.models.MoviesModel
import com.homindolentrahar.misbar.others.constants.Constants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesRxPagingSource(
    private val remote: MoviesRemoteDataSource,
    private val genreId: Int = -1
) : RxPagingSource<Int, MoviesModel>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MoviesModel>> {
        val page = params.key ?: Constants.DEFAULT_PAGE_INDEX

        return when (genreId) {
            -1 -> getMoviesWithGenres(remote.getPopularMovies(page))
            else -> getMoviesWithGenres(remote.getMoviesByGenre(page, genreId))
        }
            .subscribeOn(Schedulers.io())
            .map { responses ->
                return@map LoadResult.Page(
                    responses,
                    if (page == Constants.DEFAULT_PAGE_INDEX) null else page - 1,
                    nextKey = if (responses.isNullOrEmpty()) null else page + 1,
                ) as LoadResult<Int, MoviesModel>
            }
            .onErrorReturn { throwable -> LoadResult.Error(throwable) }
    }

    private fun getMoviesWithGenres(single: Single<MoviesDtoResponse>): Single<List<MoviesModel>> {
        return Single.zip(
            single,
            remote.getMoviesGenres(),
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