package com.homindolentrahar.misbar.domain.models

data class ShowsModel(
    val firstAirDate: String,
    val overview: String,
    val voteAverage: Double,
    val name: String,
    val id: Int,
    val posterPath: String,
) {
    var genres: List<GenresModel> = listOf()
}
