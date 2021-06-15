package com.homindolentrahar.misbar.data.repositories

import androidx.paging.*
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class FakeFavoritesRepositoryAndroid : FavoritesRepository {

    private val dummyPagingData = PagingData.from(DummyData.generateFavoritesModel())
    private val dummySingleData = DummyData.generateFavoritesModel()[0]

    override fun getAllFavoriteItems(): Observable<PagingData<FavoritesModel>> {
        return Observable.just(dummyPagingData)
    }

    override fun getAllFavoriteItemsByType(type: String): Observable<PagingData<FavoritesModel>> {
        return Observable.just(dummyPagingData)
    }

    override fun getSingleFavoriteItem(itemId: Int): Single<FavoritesModel> {
        return Single.just(dummySingleData)
    }

    override fun addFavorite(model: FavoritesModel): Completable {
        return Completable.complete()
    }

    override fun deleteFavorite(model: FavoritesModel): Completable {
        return Completable.complete()
    }
}