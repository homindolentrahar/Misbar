package com.homindolentrahar.misbar.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.RxImmediateSchedulerRule
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.others.wrappers.Status
import com.homindolentrahar.misbar.utils.getOrAwaitValueTest
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: FavoritesRepository

    @Mock
    private lateinit var favoritesFragmentObserver: Observer<Resource<PagingData<FavoritesModel>>>

    private lateinit var favoritesViewModel: FavoritesViewModel

    private val dummyPagingData = PagingData.from(DummyData.generateFavoritesModel())
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Before
    fun setup() {
        favoritesViewModel = FavoritesViewModel(repository)
    }

    @Test
    fun `getFavoritesData returns correct response`() {
        `when`(repository.getAllFavoriteItems()).thenReturn(Observable.just(dummyPagingData))

        favoritesViewModel.getFavoritesData()
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItems()

        repository.getAllFavoriteItems()
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getFavoritesData returns error`() {
        `when`(repository.getAllFavoriteItems()).thenReturn(Observable.error(dummyErrorResponse))

        favoritesViewModel.getFavoritesData()
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItems()

        repository.getAllFavoriteItems()
            .test()
            .await()
            .assertError(dummyErrorResponse)
            .assertNoValues()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getFavoritesDataByType movies returns correct response`() {
        `when`(repository.getAllFavoriteItemsByType(anyString())).thenReturn(
            Observable.just(
                dummyPagingData
            )
        )

        favoritesViewModel.getFavoritesDataByType("movies")
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItemsByType("movies")

        repository.getAllFavoriteItemsByType("movies")
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getFavoritesDataByType movies returns error`() {
        `when`(repository.getAllFavoriteItemsByType(anyString())).thenReturn(
            Observable.error(
                dummyErrorResponse
            )
        )

        favoritesViewModel.getFavoritesDataByType("movies")
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItemsByType("movies")

        repository.getAllFavoriteItemsByType("movies")
            .test()
            .await()
            .assertError(dummyErrorResponse)
            .assertNoValues()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getFavoritesDataByType shows returns correct response`() {
        `when`(repository.getAllFavoriteItemsByType(anyString())).thenReturn(
            Observable.just(
                dummyPagingData
            )
        )

        favoritesViewModel.getFavoritesDataByType("shows")
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItemsByType("shows")

        repository.getAllFavoriteItemsByType("shows")
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getFavoritesDataByType shows returns error`() {
        `when`(repository.getAllFavoriteItemsByType(anyString())).thenReturn(
            Observable.error(
                dummyErrorResponse
            )
        )

        favoritesViewModel.getFavoritesDataByType("shows")
        val favoritesFragmentData = favoritesViewModel.favoritesData

        verify(repository).getAllFavoriteItemsByType("shows")

        repository.getAllFavoriteItemsByType("shows")
            .test()
            .await()
            .assertError(dummyErrorResponse)
            .assertNoValues()
            .dispose()

        assertThat(favoritesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        favoritesFragmentData.observeForever(favoritesFragmentObserver)
        verify(favoritesFragmentObserver).onChanged(favoritesFragmentData.getOrAwaitValueTest())
    }

}