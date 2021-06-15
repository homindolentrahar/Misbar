package com.homindolentrahar.misbar.di.modules

import androidx.paging.PagingConfig
import com.homindolentrahar.misbar.data.repositories.FavoritesRepositoryImpl
import com.homindolentrahar.misbar.data.repositories.MoviesRepositoryImpl
import com.homindolentrahar.misbar.data.repositories.SearchRepositoryImpl
import com.homindolentrahar.misbar.data.repositories.ShowsRepositoryImpl
import com.homindolentrahar.misbar.data.sources.local.FavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.RoomFavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDao
import com.homindolentrahar.misbar.data.sources.local.database.GenresDao
import com.homindolentrahar.misbar.data.sources.remote.*
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

//    Remote

    @Provides
    @ViewModelScoped
    fun provideMoviesRemoteDataSource(
        tmdbApi: TMDBApi,
    ): MoviesRemoteDataSource =
        RetrofitMoviesRemoteDataSource(tmdbApi)

    @Provides
    @ViewModelScoped
    fun provideShowsRemoteDataSource(tmdbApi: TMDBApi): ShowsRemoteDataSource =
        RetrofitShowsRemoteDataSource(tmdbApi)

    @Provides
    @ViewModelScoped
    fun provideSearchRemoteDataSource(tmdbApi: TMDBApi): SearchRemoteDataSource =
        RetrofitSearchRemoteDataSource(tmdbApi)

    @ExperimentalCoroutinesApi
    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(
        remote: MoviesRemoteDataSource,
        pagingConfig: PagingConfig
    ): MoviesRepository =
        MoviesRepositoryImpl(remote, pagingConfig)

    @ExperimentalCoroutinesApi
    @Provides
    @ViewModelScoped
    fun provideShowsRepository(
        remote: ShowsRemoteDataSource,
        pagingConfig: PagingConfig
    ): ShowsRepository =
        ShowsRepositoryImpl(remote, pagingConfig)

    @ExperimentalCoroutinesApi
    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        remote: SearchRemoteDataSource,
        pagingConfig: PagingConfig
    ): SearchRepository =
        SearchRepositoryImpl(remote, pagingConfig)

//    Local

    @Provides
    @ViewModelScoped
    fun provideFavoriteLocalDataSource(
        favoritesDao: FavoritesDao,
        genresDao: GenresDao
    ): FavoritesLocalDataSource =
        RoomFavoritesLocalDataSource(favoritesDao, genresDao)

    @Provides
    @ViewModelScoped
    @ExperimentalCoroutinesApi
    fun provideFavoriteRepository(
        dataSource: FavoritesLocalDataSource,
        pagingConfig: PagingConfig
    ): FavoritesRepository =
        FavoritesRepositoryImpl(dataSource, pagingConfig)

}