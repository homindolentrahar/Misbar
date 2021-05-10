package com.homindolentrahar.misbar.data.core

import com.homindolentrahar.misbar.R
import com.homindolentrahar.misbar.domain.core.ItemModel

data class ItemLocalEntity(
    var id: Int = 0,
    var title: String = "No Title",
    var synopsis: String = "No Synopsis",
    var director: String = "",
    var releaseDate: String = "",
    var rating: Double = 0.0,
    var duration: String = "No Duration",
    var language: String = "No Language",
    var revenue: Int = 0,
    var ageRating: String = "No Age Rating",
    var imgPoster: Int = R.drawable.placeholder_image,
    var genres: List<String> = listOf(),
) {

    fun toModel(): ItemModel {
        return ItemModel(
            id,
            title,
            synopsis,
            director,
            releaseDate,
            rating,
            duration,
            language,
            revenue,
            ageRating,
            imgPoster,
            genres
        )
    }
}