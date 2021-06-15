package com.homindolentrahar.misbar.data.sources.local

import com.homindolentrahar.misbar.data.sources.local.database.FavoritesDao
import com.homindolentrahar.misbar.data.sources.local.database.GenresDao
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RoomFavoritesLocalDataSourceTest {

    @Mock
    private lateinit var favoritesDao: FavoritesDao

    @Mock
    private lateinit var genresDao: GenresDao

    private lateinit var localDataSource: FavoritesLocalDataSource

    private val dummyFavoritesWithGenres = DummyData.generateFavoritesWithGenres()
    private val dummySingleFavoritesWithGenres = dummyFavoritesWithGenres[0]
    private val dummySingleItemId = dummySingleFavoritesWithGenres.favorites.id
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Before
    fun setup() {
        localDataSource = RoomFavoritesLocalDataSource(favoritesDao, genresDao)
    }

    @Test
    fun `getAllFavoriteItems return correct response`() {
        localDataSource.getAllFavoriteItems()

        verify(favoritesDao).getAllFavoriteItems()

        assertThat(localDataSource.getAllFavoriteItems()).isEqualTo(favoritesDao.getAllFavoriteItems())
    }

    @Test
    fun `getAllFavoriteItemsByType movie returns correct response`() {
        localDataSource.getAllFavoriteItemsByType("movies")

        verify(favoritesDao).getAllFavoriteItemsByType("movies")

        assertThat(localDataSource.getAllFavoriteItemsByType("movies")).isEqualTo(
            favoritesDao.getAllFavoriteItemsByType(
                "movies"
            )
        )
    }

    @Test
    fun `getAllFavoriteItemsByType tv returns correct response`() {
        localDataSource.getAllFavoriteItemsByType("shows")

        verify(favoritesDao).getAllFavoriteItemsByType("shows")

        assertThat(localDataSource.getAllFavoriteItemsByType("shows")).isEqualTo(
            favoritesDao.getAllFavoriteItemsByType(
                "shows"
            )
        )
    }


    @Test
    fun `getSingleFavoriteItem returns success`() {
        `when`(favoritesDao.getSingleFavoriteItem(anyInt())).thenReturn(
            Single.just(
                dummySingleFavoritesWithGenres
            )
        )

        val single = localDataSource.getSingleFavoriteItem(dummySingleItemId)

        verify(favoritesDao).getSingleFavoriteItem(dummySingleItemId)

        single
            .test()
            .await()
            .assertValue(dummySingleFavoritesWithGenres)
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getSingleFavoriteItem returns error`() {
        `when`(favoritesDao.getSingleFavoriteItem(anyInt())).thenReturn(
            Single.error(
                dummyErrorResponse
            )
        )

        val single = localDataSource.getSingleFavoriteItem(dummySingleItemId)

        verify(favoritesDao).getSingleFavoriteItem(dummySingleItemId)

        single
            .test()
            .await()
            .assertError(dummyErrorResponse)
            .assertValueCount(0)
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `addFavorite success`() {
        `when`(favoritesDao.addFavorite(dummySingleFavoritesWithGenres.favorites)).thenReturn(
            Completable.complete()
        )

        val completable = localDataSource.addFavorite(dummySingleFavoritesWithGenres.favorites)

        verify(favoritesDao).addFavorite(dummySingleFavoritesWithGenres.favorites)

        completable
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `addFavorite error`() {
        `when`(favoritesDao.addFavorite(dummySingleFavoritesWithGenres.favorites)).thenReturn(
            Completable.error(dummyErrorResponse)
        )

        val completable = localDataSource.addFavorite(dummySingleFavoritesWithGenres.favorites)

        verify(favoritesDao).addFavorite(dummySingleFavoritesWithGenres.favorites)

        completable
            .test()
            .await()
            .assertNotComplete()
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `deleteFavorite success`() {
        `when`(favoritesDao.deleteFavorite(dummySingleFavoritesWithGenres.favorites)).thenReturn(
            Completable.complete()
        )

        val completable = localDataSource.deleteFavorite(dummySingleFavoritesWithGenres.favorites)

        verify(favoritesDao).deleteFavorite(dummySingleFavoritesWithGenres.favorites)

        completable
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `deleteFavorite error`() {
        `when`(favoritesDao.deleteFavorite(dummySingleFavoritesWithGenres.favorites)).thenReturn(
            Completable.error(dummyErrorResponse)
        )

        val completable = localDataSource.deleteFavorite(dummySingleFavoritesWithGenres.favorites)

        verify(favoritesDao).deleteFavorite(dummySingleFavoritesWithGenres.favorites)

        completable
            .test()
            .await()
            .assertNotComplete()
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `addGenres success`() {
        `when`(genresDao.addGenres(anyList())).thenReturn(Completable.complete())

        val completable = localDataSource.addGenres(dummySingleFavoritesWithGenres.genres)

        verify(genresDao).addGenres(dummySingleFavoritesWithGenres.genres)

        completable
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `addGenres error`() {
        `when`(genresDao.addGenres(anyList())).thenReturn(Completable.error(dummyErrorResponse))

        val completable = localDataSource.addGenres(dummySingleFavoritesWithGenres.genres)

        verify(genresDao).addGenres(dummySingleFavoritesWithGenres.genres)

        completable
            .test()
            .await()
            .assertNotComplete()
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `deleteGenres success`() {
        `when`(genresDao.deleteGenres(anyList())).thenReturn(Completable.complete())

        val completable = localDataSource.deleteGenres(dummySingleFavoritesWithGenres.genres)

        verify(genresDao).deleteGenres(dummySingleFavoritesWithGenres.genres)

        completable
            .test()
            .await()
            .assertComplete()
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `deleteGenres error`() {
        `when`(genresDao.deleteGenres(anyList())).thenReturn(Completable.error(dummyErrorResponse))

        val completable = localDataSource.deleteGenres(dummySingleFavoritesWithGenres.genres)

        verify(genresDao).deleteGenres(dummySingleFavoritesWithGenres.genres)

        completable
            .test()
            .await()
            .assertNotComplete()
            .assertNoValues()
            .dispose()
    }
}
