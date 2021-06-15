package com.homindolentrahar.misbar.data.sources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homindolentrahar.misbar.domain.models.FavoritesModel

@Entity(tableName = "favorite_items")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val type: String
) {
    companion object {
        fun fromModel(model: FavoritesModel): FavoritesEntity =
            FavoritesEntity(
                model.id,
                model.name,
                model.overview,
                model.releaseDate,
                model.voteAverage,
                model.posterPath,
                model.type.value
            )

//        fun FavoritesEntity.toModel(): FavoritesModel =
//            FavoritesModel(
//                id,
//                name,
//                overview,
//                releaseDate,
//                listOf(),
//                voteAverage,
//                posterPath,
//                FavoriteItemType.valueOf(StringMapper.capitalize(type))
//            )
    }
}
