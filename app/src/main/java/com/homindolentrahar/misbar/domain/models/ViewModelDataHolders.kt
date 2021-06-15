package com.homindolentrahar.misbar.domain.models

import androidx.paging.PagingData

data class MoviesFragmentData(
    val listData: List<MoviesModel> = listOf(),
    val pagedListData: PagingData<MoviesModel>,
)

data class DetailWithFavorite(
    val detail: DetailMoviesModel,
    val singleFavorite: FavoritesModel?
)

data class ShowsFragmentData(
    val listData: List<ShowsModel> = listOf(),
    val pagedListData: PagingData<ShowsModel>
)
