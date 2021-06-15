package com.homindolentrahar.misbar.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GenresModel(
    val id: Int,
    val name: String,
)

@Parcelize
data class LocalGenresModel(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val description: String,
) : Parcelable