package com.homindolentrahar.misbar.data.sources.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity
import io.reactivex.rxjava3.core.Completable

@Dao
interface GenresDao {
    @Insert
    fun addGenres(genres: List<GenresEntity>): Completable

    @Delete
    fun deleteGenres(genres: List<GenresEntity>): Completable
}