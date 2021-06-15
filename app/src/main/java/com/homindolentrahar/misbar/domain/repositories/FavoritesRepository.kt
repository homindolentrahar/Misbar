package com.homindolentrahar.misbar.domain.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.models.GenresModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface FavoritesRepository {

    fun getAllFavoriteItems(): Observable<PagingData<FavoritesModel>>

    fun getAllFavoriteItemsByType(type: String): Observable<PagingData<FavoritesModel>>

    fun getSingleFavoriteItem(itemId: Int): Single<FavoritesModel>

    fun addFavorite(model: FavoritesModel): Completable

    fun deleteFavorite(model: FavoritesModel): Completable

}