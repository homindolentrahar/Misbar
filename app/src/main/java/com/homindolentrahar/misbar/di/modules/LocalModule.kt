package com.homindolentrahar.misbar.di.modules

import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDao
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDatabase
import com.homindolentrahar.misbar.data.sources.local.database.GenresDao
import com.homindolentrahar.misbar.others.constants.FavoriteDatabaseContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoritesDatabase =
        Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            FavoriteDatabaseContracts.FAVORITE_DB_NAME
        )
            .build()

    @Singleton
    @Provides
    fun provideFavoritesDao(database: FavoritesDatabase): FavoritesDao =
        database.favoritesDao()

    @Singleton
    @Provides
    fun provideGenresDao(database: FavoritesDatabase): GenresDao =
        database.genresDao()

    @Singleton
    @Provides
    fun provideFavoritePagingConfig(): PagingConfig =
        PagingConfig(
            enablePlaceholders = true,
            initialLoadSize = 50,
            pageSize = 10,
            maxSize = 100,
        )

}