//package com.homindolentrahar.misbar.data.sources.local
//
//import androidx.paging.PagingState
//import androidx.paging.rxjava3.RxPagingSource
//import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres.Companion.toModel
//import com.homindolentrahar.misbar.domain.models.FavoritesModel
//import com.homindolentrahar.misbar.others.constants.Constants
//import io.reactivex.rxjava3.core.Single
//import io.reactivex.rxjava3.schedulers.Schedulers
//
//class FavoritesRxPagingSource(
//    private val local: FavoritesLocalDataSource,
//    private val type: String = "",
//) : RxPagingSource<Int, FavoritesModel>() {
//    override fun getRefreshKey(state: PagingState<Int, FavoritesModel>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FavoritesModel>> {
//        val page = params.key ?: Constants.DEFAULT_PAGE_INDEX
//
//        return when (type) {
//            "" -> local.getAllFavoriteItems(params.loadSize)
//            else -> local.getAllFavoriteItemsByType(params.loadSize, type)
//        }
//            .map { favoritesWithGenres -> favoritesWithGenres.map { it.toModel() } }
//            .subscribeOn(Schedulers.io())
//            .map { responses ->
//                return@map LoadResult.Page(
//                    responses,
//                    if (page == Constants.DEFAULT_PAGE_INDEX) null else page - 1,
//                    nextKey = if (responses.isNullOrEmpty()) null else page + 1,
//                ) as LoadResult<Int, FavoritesModel>
//            }
//            .onErrorReturn { throwable -> LoadResult.Error(throwable) }
//    }
//}