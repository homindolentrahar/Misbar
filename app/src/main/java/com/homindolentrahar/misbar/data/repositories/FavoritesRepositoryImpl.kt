package com.homindolentrahar.misbar.data.repositories

import androidx.paging.*
import androidx.paging.rxjava3.observable
import com.homindolentrahar.misbar.data.sources.local.FavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.entities.*
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres.Companion.toModel
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FavoritesRepositoryImpl(
    private val dataSource: FavoritesLocalDataSource,
    private val pagingConfig: PagingConfig
) : FavoritesRepository {

    override fun getAllFavoriteItems(): Observable<PagingData<FavoritesModel>> =
        Pager(
            config = pagingConfig,
            initialKey = null,
            pagingSourceFactory = { dataSource.getAllFavoriteItems() }
        )
            .observable
            .map { pagingData -> pagingData.map { favoriteWithGenres -> favoriteWithGenres.toModel() } }

    override fun getAllFavoriteItemsByType(type: String): Observable<PagingData<FavoritesModel>> =
        Pager(
            config = pagingConfig,
            initialKey = null,
            pagingSourceFactory = { dataSource.getAllFavoriteItemsByType(type) }
        )
            .observable
            .map { pagingData -> pagingData.map { favoriteWithGenres -> favoriteWithGenres.toModel() } }

    override fun getSingleFavoriteItem(itemId: Int): Single<FavoritesModel> =
        dataSource.getSingleFavoriteItem(itemId).map { it?.toModel() }

    override fun addFavorite(model: FavoritesModel): Completable =
        dataSource.addFavorite(FavoritesEntity.fromModel(model))
            .andThen(dataSource.addGenres(model.genres.map {
                GenresEntity.fromModel(it, model.id)
            }))

    override fun deleteFavorite(model: FavoritesModel): Completable =
        dataSource.deleteFavorite(FavoritesEntity.fromModel(model))
            .andThen(dataSource.deleteGenres(model.genres.map {
                GenresEntity.fromModel(it, model.id)
            }))
}