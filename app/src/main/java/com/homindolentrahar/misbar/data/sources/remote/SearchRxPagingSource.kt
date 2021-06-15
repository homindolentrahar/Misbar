package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.homindolentrahar.misbar.data.sources.remote.dtos.SearchDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.others.constants.Constants
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchRxPagingSource(
    private val remote: SearchRemoteDataSource,
    private val query: String,
) : RxPagingSource<Int, SearchModel>() {

    override fun getRefreshKey(state: PagingState<Int, SearchModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SearchModel>> {
        val page = params.key ?: Constants.DEFAULT_PAGE_INDEX

        return getSearchWithGenres(remote.searchItems(page, query))
            .subscribeOn(Schedulers.io())
            .map { responses ->
                return@map LoadResult.Page(
                    responses,
                    if (page == Constants.DEFAULT_PAGE_INDEX) null else page - 1,
                    nextKey = if (responses.isNullOrEmpty()) null else page + 1,
                ) as LoadResult<Int, SearchModel>
            }
            .onErrorReturn { throwable -> LoadResult.Error(throwable) }
    }

    private fun getSearchWithGenres(single: Single<SearchDtoResponse>): Single<List<SearchModel>> {
        return Single.zip(
            single,
            remote.getMoviesGenres(),
            remote.getShowsGenres(),
            { dtoResponse, moviesGenresDtoResponse, showsGenresDtoResponse ->
                dtoResponse.results
                    ?.filter { searchDto -> searchDto.mediaType == "movie" || searchDto.mediaType == "tv" }
                    ?.map { searchDto ->
                        searchDto.toModel().apply {
                            val genresResponse =
                                if (searchDto.mediaType == "movie") moviesGenresDtoResponse else showsGenresDtoResponse
                            this.genres = genresResponse.genres?.filter { genresDto ->
                                searchDto.genreIds!!.contains(genresDto.id)
                            }?.take(3)?.toModels() as List<GenresModel>
                        }
                    }
            }
        )
    }
}