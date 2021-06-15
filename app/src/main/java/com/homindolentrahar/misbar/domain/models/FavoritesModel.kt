package com.homindolentrahar.misbar.domain.models

import com.homindolentrahar.misbar.others.constants.FavoriteItemType

data class FavoritesModel(
    val id: Int,
    val name: String,
    val overview: String,
    val releaseDate: String,
    val genres: List<GenresModel>,
    val voteAverage: Double,
    val posterPath: String,
    val type: FavoriteItemType
) {
    companion object{
        fun fromDetailMoviesModel(model: DetailMoviesModel): FavoritesModel =
            FavoritesModel(
                model.id,
                model.title,
                model.overview,
                model.releaseDate,
                model.genres,
                model.voteAverage,
                model.posterPath,
                FavoriteItemType.Movies,
            )

        fun fromDetailShowsModel(model: DetailShowsModel): FavoritesModel =
            FavoritesModel(
                model.id,
                model.name,
                model.overview,
                model.firstAirDate,
                model.genres,
                model.voteAverage,
                model.posterPath,
                FavoriteItemType.Shows,
            )
    }
}