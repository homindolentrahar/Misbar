package com.homindolentrahar.misbar.di.modules

import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room
import com.homindolentrahar.misbar.data.repositories.FakeFavoritesRepositoryAndroid
import com.homindolentrahar.misbar.data.sources.local.FavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.RoomFavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDao
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDatabase
import com.homindolentrahar.misbar.data.sources.local.database.GenresDao
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named

@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object TestLocalModule {

    @Named("test_db")
    @Provides
    fun provideInMemoryFavoriteDatabase(@ApplicationContext context: Context): FavoritesDatabase =
        Room.inMemoryDatabaseBuilder(context, FavoritesDatabase::class.java)
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                }
//
//                override fun onOpen(db: SupportSQLiteDatabase) {
//                    super.onOpen(db)
//                }
//            })
            .allowMainThreadQueries()
            .build()

    @Named("favorites_dao")
    @Provides
    fun provideFavoritesDao(database: FavoritesDatabase): FavoritesDao =
        database.favoritesDao()

    @Named("genres_dao")
    @Provides
    fun provideGenresDao(database: FavoritesDatabase): GenresDao =
        database.genresDao()

    @Named("paging_config")
    @Provides
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 50,
        maxSize = 100,
        enablePlaceholders = true,
    )

    @Named("favorites_local_data_source")
    @Provides
    fun provideFavoritesLocalDataSource(
        @Named("favorites_dao") favoritesDao: FavoritesDao,
        @Named("genres_dao") genresDao: GenresDao,
    ): FavoritesLocalDataSource =
        RoomFavoritesLocalDataSource(favoritesDao, genresDao)

    @Named("favorites_repository")
    @Provides
    fun provideFavoritesRepository(): FavoritesRepository = FakeFavoritesRepositoryAndroid()

}