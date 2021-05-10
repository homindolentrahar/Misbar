package com.homindolentrahar.misbar.domain.core

import com.homindolentrahar.misbar.R

data class ItemModel(
    var id: Int,
    var title: String,
    var synopsis: String,
    var director: String,
    var releaseDate: String,
    var rating: Double,
    var duration: String,
    var language: String,
    var revenue: Int,
    var ageRating: String,
    var imgPoster: Int,
    var genres: List<String>,
) {
    companion object {
        fun empty(): ItemModel {
            return ItemModel(
                0,
                "No Title",
                "No Synopsis",
                "",
                "",
                0.0,
                "No Duration",
                "No Language",
                0,
                "No Age Rating",
                R.drawable.ic_placeholder_image,
                listOf()
            )
        }
    }
}