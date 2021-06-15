package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.homindolentrahar.misbar.data.sources.remote.dtos.ShowsDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.domain.models.ShowsModel
import com.homindolentrahar.misbar.others.constants.Constants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowsRxPagingSource(
    private val remote: ShowsRemoteDataSource,
    private val genreId: Int = -1
) : RxPagingSource<Int, ShowsModel>() {

    override fun getRefreshKey(state: PagingState<Int, ShowsModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ShowsModel>> {
        val page = params.key ?: Constants.DEFAULT_PAGE_INDEX

        return when (genreId) {
            -1 -> getShowsWithGenres(remote.getPopularShows(page))
            else -> getShowsWithGenres(remote.getShowsByGenre(page, genreId))
        }
            .subscribeOn(Schedulers.io())
            .map { responses ->
                return@map LoadResult.Page(
                    responses,
                    if (page == Constants.DEFAULT_PAGE_INDEX) null else page - 1,
                    nextKey = if (responses.isNullOrEmpty()) null else page + 1,
                ) as LoadResult<Int, ShowsModel>
            }
            .onErrorReturn { throwable -> LoadResult.Error(throwable) }
    }

    private fun getShowsWithGenres(single: Single<ShowsDtoResponse>): Single<List<ShowsModel>> {
        return Single.zip(
            single,
            remote.getShowsGenres(),
            { dtoResponse, genresDtoResponse ->
                dtoResponse.results?.map { showsDto ->
                    showsDto.toModel().apply {
                        this.genres = genresDtoResponse.genres?.filter { genresDto ->
                            showsDto.genreIds!!.contains(genresDto.id)
                        }?.take(3)?.toModels() as List<GenresModel>
                    }
                }
            }
        )
    }
}