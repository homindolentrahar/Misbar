package com.homindolentrahar.misbar.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homindolentrahar.misbar.domain.models.GenresModel

@Entity(tableName = "genre_items")
data class GenresEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemId: Int,
    val genreId: Int,
    val name: String
) {
    companion object {
        fun fromModel(model: GenresModel, itemId: Int): GenresEntity =
            GenresEntity(
                itemId = itemId,
                genreId = model.id,
                name = model.name
            )

        fun GenresEntity.toModel(): GenresModel =
            GenresModel(genreId, name)
    }
}