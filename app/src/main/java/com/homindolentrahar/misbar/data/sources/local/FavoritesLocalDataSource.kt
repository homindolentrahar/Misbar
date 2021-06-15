package com.homindolentrahar.misbar.data.sources.local

import androidx.paging.PagingSource
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDao
import com.homindolentrahar.misbar.data.sources.local.database.GenresDao
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesEntity
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres
import com.homindolentrahar.misbar.data.sources.local.entities.GenresEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface FavoritesLocalDataSource {
    fun getAllFavoriteItems(): PagingSource<Int, FavoritesWithGenres>

    fun getAllFavoriteItemsByType(type: String): PagingSource<Int, FavoritesWithGenres>

    fun getSingleFavoriteItem(itemId: Int): Single<FavoritesWithGenres>

    fun addFavorite(entity: FavoritesEntity): Completable

    fun deleteFavorite(entity: FavoritesEntity): Completable

    fun addGenres(genres: List<GenresEntity>): Completable

    fun deleteGenres(genres: List<GenresEntity>): Completable
}

class RoomFavoritesLocalDataSource(
    private val favoritesDao: FavoritesDao,
    private val genresDao: GenresDao
) :
    FavoritesLocalDataSource {
    override fun getAllFavoriteItems(): PagingSource<Int, FavoritesWithGenres> {
        return favoritesDao.getAllFavoriteItems()
    }

    override fun getAllFavoriteItemsByType(type: String): PagingSource<Int, FavoritesWithGenres> {
        return favoritesDao.getAllFavoriteItemsByType(type)
    }

    override fun getSingleFavoriteItem(itemId: Int): Single<FavoritesWithGenres> {
        return favoritesDao.getSingleFavoriteItem(itemId)
    }

    override fun addFavorite(entity: FavoritesEntity): Completable {
        return favoritesDao.addFavorite(entity)
    }

    override fun deleteFavorite(entity: FavoritesEntity): Completable {
        return favoritesDao.deleteFavorite(entity)
    }

    override fun addGenres(genres: List<GenresEntity>): Completable {
        return genresDao.addGenres(genres)
    }

    override fun deleteGenres(genres: List<GenresEntity>): Completable {
        return genresDao.deleteGenres(genres)
    }
}