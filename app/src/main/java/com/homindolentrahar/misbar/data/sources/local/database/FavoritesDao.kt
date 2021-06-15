package com.homindolentrahar.misbar.data.sources.local.database

import androidx.paging.PagingSource
import androidx.paging.rxjava3.RxPagingSource
import androidx.room.*
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesEntity
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoritesDao {

    @Transaction
    @Query("SELECT * FROM favorite_items ORDER BY name ASC")
    fun getAllFavoriteItems(): PagingSource<Int, FavoritesWithGenres>

//    @Transaction
//    @Query("SELECT * FROM favorite_items ORDER BY name ASC LIMIT :loadSize")
//    fun getAllFavoriteItems(loadSize: Int): Single<List<FavoritesWithGenres>>
//

    @Transaction
    @Query("SELECT * FROM favorite_items WHERE type = :type ORDER BY name ASC")
    fun getAllFavoriteItemsByType(type: String): PagingSource<Int, FavoritesWithGenres>

//
//    @Transaction
//    @Query("SELECT * FROM favorite_items WHERE type = :type ORDER BY name ASC LIMIT :loadSize")
//    fun getAllFavoriteItemsByType(loadSize: Int, type: String): Single<List<FavoritesWithGenres>>
//

    @Transaction
    @Query("SELECT * FROM favorite_items WHERE id = :itemId")
    fun getSingleFavoriteItem(itemId: Int): Single<FavoritesWithGenres>

    @Insert
    fun addFavorite(entity: FavoritesEntity): Completable

    @Delete
    fun deleteFavorite(entity: FavoritesEntity): Completable
}