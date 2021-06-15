package com.homindolentrahar.misbar.data.sources.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity.Companion.toModel
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.others.constants.FavoriteItemType
import com.homindolentrahar.misbar.utils.mappers.StringMapper

data class FavoritesWithGenres(
    @Embedded val favorites: FavoritesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "itemId"
    )
    val genres: List<GenresEntity>,
) {
    companion object {
        fun FavoritesWithGenres.toModel(): FavoritesModel =
            FavoritesModel(
                favorites.id,
                favorites.name,
                favorites.overview,
                favorites.releaseDate,
                genres.map { it.toModel() },
                favorites.voteAverage,
                favorites.posterPath,
                FavoriteItemType.valueOf(StringMapper.capitalize(favorites.type)),
            )
    }
}
