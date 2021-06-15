package com.homindolentrahar.misbar.data.repositories

import androidx.paging.*
import com.homindolentrahar.misbar.data.sources.local.FavoritesLocalDataSource
import com.homindolentrahar.misbar.data.sources.local.entities.FavoritesWithGenres
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesRepositoryImplTest {

    @Mock
    private lateinit var localDataSource: FavoritesLocalDataSource

    private lateinit var repository: FavoritesRepository

    private val dummyFavoritesWithGenres = DummyData.generateFavoritesWithGenres()
    private val dummyFavoritesModel = DummyData.generateFavoritesModel()
    private val dummyPagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 50,
        maxSize = 100,
        enablePlaceholders = true
    )
    private val dummySingleItem = dummyFavoritesModel[0]
    private val dummySingleItemId = dummySingleItem.id

    @Before
    fun setup() {
        repository = FavoritesRepositoryImpl(localDataSource, dummyPagingConfig)
    }

    @Test
    fun `getAllFavoriteItems testing`() {
        runBlockingTest {
            val mockPagingSource =
                mock(PagingSource::class.java) as PagingSource<Int, FavoritesWithGenres>

            `when`(localDataSource.getAllFavoriteItems()).thenReturn(mockPagingSource)

            repository.getAllFavoriteItems()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .dispose()
        }
    }

    @Test
    fun `getAllFavoriteItemsByType testing`() {
        runBlockingTest {
            val mockPagingSource =
                mock(PagingSource::class.java) as PagingSource<Int, FavoritesWithGenres>

            `when`(localDataSource.getAllFavoriteItemsByType("movies")).thenReturn(mockPagingSource)

            repository.getAllFavoriteItemsByType("movies")
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .dispose()
        }
    }

    @Test
    fun `getSingleItem returns testing`() {
        `when`(localDataSource.getSingleFavoriteItem(anyInt())).thenReturn(
            Single.just(
                dummyFavoritesWithGenres[0]
            )
        )

        val observableResponse = repository.getSingleFavoriteItem(dummySingleItemId)

        verify(localDataSource).getSingleFavoriteItem(dummySingleItemId)

        observableResponse
            .test()
            .await()
            .assertValue(dummySingleItem)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `addFavorite testing`() {
        `when`(localDataSource.addFavorite(dummyFavoritesWithGenres[0].favorites)).thenReturn(
            Completable.complete()
        )
        `when`(localDataSource.addGenres(anyList())).thenReturn(Completable.complete())

        val completableAction = repository.addFavorite(dummyFavoritesModel[0])

        verify(localDataSource).addFavorite(dummyFavoritesWithGenres[0].favorites)

        completableAction
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `deleteFavorite testing`() {
        `when`(localDataSource.deleteFavorite(dummyFavoritesWithGenres[0].favorites)).thenReturn(
            Completable.complete()
        )
        `when`(localDataSource.deleteGenres(anyList())).thenReturn(Completable.complete())

        val completableAction = repository.deleteFavorite(dummyFavoritesModel[0])

        verify(localDataSource).deleteFavorite(dummyFavoritesWithGenres[0].favorites)

        completableAction
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }
}