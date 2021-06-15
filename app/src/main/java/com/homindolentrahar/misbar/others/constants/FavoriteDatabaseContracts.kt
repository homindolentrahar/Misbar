package com.homindolentrahar.misbar.others.constants

enum class FavoriteItemType(val value: String) {
    Movies(FavoriteDatabaseContracts.TYPE_MOVIES),
    Shows(FavoriteDatabaseContracts.TYPE_SHOWS)
}

object FavoriteDatabaseContracts {
    const val FAVORITE_DB_NAME = "favorite_items.db"
    const val TYPE_MOVIES = "movies"
    const val TYPE_SHOWS = "shows"
}