package com.homindolentrahar.misbar.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesEntity
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity

@Database(
    entities = [FavoritesEntity::class, GenresEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun genresDao(): GenresDao
}