package com.homindolentrahar.misbar.ui.shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.homindolentrahar.misbar.RxImmediateSchedulerRule
import com.homindolentrahar.misbar.domain.models.DetailShowsModel
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.models.ShowsFragmentData
import com.homindolentrahar.misbar.domain.models.ShowsModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.others.wrappers.Status
import com.homindolentrahar.misbar.utils.getOrAwaitValueTest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsViewModelTest {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: ShowsRepository

    @Mock
    private lateinit var favoritesRepository: FavoritesRepository

    @Mock
    private lateinit var showsFragmentObserver: Observer<Resource<ShowsFragmentData>>

    @Mock
    private lateinit var detailShowsObserver: Observer<Resource<DetailShowsModel>>

    @Mock
    private lateinit var singleFavoritesObserver: Observer<Resource<FavoritesModel>>

    private lateinit var viewModel: ShowsViewModel
    private val dummyShowsModel = DummyData.generateShowsModel()
    private val dummySingleFavoritesModel = DummyData.generateFavoritesModel()[0]
    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()
    private val dummyShowsId = 123456
    private val dummyGenreId = 4321
    private val dummyErrorResponse = Throwable("Cannot retrieve data")
    private val dummyPagingData = PagingData.from(dummyShowsModel)
    private val dummyShowsFragmentData = ShowsFragmentData(dummyShowsModel.take(5), dummyPagingData)

    @Before
    fun setup() {
        viewModel = ShowsViewModel(repository, favoritesRepository)
    }

    //    Shows Fragment Data
    @Test
    fun `getShowsFragmentData returns success`() {
        Mockito.`when`(repository.getTodayAiring()).thenReturn(Observable.just(dummyShowsModel))
        Mockito.`when`(repository.getPopularShows()).thenReturn(Observable.just(dummyPagingData))

        viewModel.getShowsFragmentData()
        val showsFragmentData = viewModel.showsFragmentData

        Mockito.verify(repository).getTodayAiring()
        Mockito.verify(repository).getPopularShows()

        Observable.zip(
            repository.getTodayAiring(),
            repository.getPopularShows(),
            { now, popular -> ShowsFragmentData(now.take(5), popular) }
        )
            .test()
            .await()
            .assertValue(dummyShowsFragmentData)
            .assertValue { it.listData.isNotEmpty() }
            .assertNoErrors()
            .dispose()

        Truth.assertThat(showsFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        showsFragmentData.observeForever(showsFragmentObserver)
        Mockito.verify(showsFragmentObserver).onChanged(showsFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getShowsFragmentData returns error`() {
        Mockito.`when`(repository.getTodayAiring()).thenReturn(Observable.error(dummyErrorResponse))
        Mockito.`when`(repository.getPopularShows())
            .thenReturn(Observable.error(dummyErrorResponse))

        viewModel.getShowsFragmentData()
        val showsFragmentData = viewModel.showsFragmentData

        Mockito.verify(repository).getTodayAiring()
        Mockito.verify(repository).getPopularShows()

        Observable.zip(
            repository.getTodayAiring(),
            repository.getPopularShows(),
            { _, _ -> emptyList<ShowsModel>() }
        )
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        Truth.assertThat(showsFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        showsFragmentData.observeForever(showsFragmentObserver)
        Mockito.verify(showsFragmentObserver).onChanged(showsFragmentData.getOrAwaitValueTest())
    }

    //    Shows by Genre
    @Test
    fun `getShowsByGenre returns success`() {
        Mockito.`when`(repository.getShowsByGenre(Mockito.anyInt()))
            .thenReturn(Observable.just(dummyPagingData))

        viewModel.getShowsByGenre(dummyGenreId)
        val showsFragmentData = viewModel.showsByGenre

        Mockito.verify(repository).getShowsByGenre(dummyGenreId)

        repository.getShowsByGenre(dummyGenreId)
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        Truth.assertThat(showsFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        showsFragmentData.observeForever(showsFragmentObserver)
        Mockito.verify(showsFragmentObserver).onChanged(showsFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getShowsByGenre returns error`() {
        Mockito.`when`(repository.getShowsByGenre(Mockito.anyInt()))
            .thenReturn(Observable.error(dummyErrorResponse))

        viewModel.getShowsByGenre(dummyGenreId)
        val showsFragmentData = viewModel.showsByGenre

        Mockito.verify(repository).getShowsByGenre(dummyGenreId)

        repository.getShowsByGenre(dummyGenreId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        Truth.assertThat(showsFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        showsFragmentData.observeForever(showsFragmentObserver)
        Mockito.verify(showsFragmentObserver).onChanged(showsFragmentData.getOrAwaitValueTest())
    }

    //    Shows Detail
    @Test
    fun `getShowsDetail returns success`() {
        Mockito.`when`(repository.getShowsDetail(Mockito.anyInt()))
            .thenReturn(Single.just(dummyDetailShowsModel))

        viewModel.getShowsDetail(dummyShowsId)
        val detailShowsData = viewModel.detailShowsData

        Mockito.verify(repository).getShowsDetail(dummyShowsId)

        repository.getShowsDetail(dummyShowsId)
            .test()
            .await()
            .assertValue(dummyDetailShowsModel)
            .assertNoErrors()
            .dispose()

        Truth.assertThat(detailShowsData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        detailShowsData.observeForever(detailShowsObserver)
        Mockito.verify(detailShowsObserver).onChanged(detailShowsData.getOrAwaitValueTest())
    }

    @Test
    fun `getShowsDetail returns error`() {
        Mockito.`when`(repository.getShowsDetail(Mockito.anyInt()))
            .thenReturn(Single.error(dummyErrorResponse))

        viewModel.getShowsDetail(dummyShowsId)
        val detailShowsData = viewModel.detailShowsData

        Mockito.verify(repository).getShowsDetail(dummyShowsId)

        repository.getShowsDetail(dummyShowsId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        Truth.assertThat(detailShowsData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        detailShowsData.observeForever(detailShowsObserver)
        Mockito.verify(detailShowsObserver).onChanged(detailShowsData.getOrAwaitValueTest())
    }

    @Test
    fun `getSingleFavoriteMovie returns correct response`() {
        Mockito.`when`(favoritesRepository.getSingleFavoriteItem(Mockito.anyInt())).thenReturn(
            Single.just(
                dummySingleFavoritesModel
            )
        )

        viewModel.getSingleFavoriteMovie(dummyShowsId)
        val singleFavoriteData = viewModel.singleFavoriteData

        Mockito.verify(favoritesRepository).getSingleFavoriteItem(dummyShowsId)

        favoritesRepository.getSingleFavoriteItem(dummyShowsId)
            .test()
            .await()
            .assertValue(dummySingleFavoritesModel)
            .assertNoErrors()
            .dispose()

        Truth.assertThat(singleFavoriteData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        singleFavoriteData.observeForever(singleFavoritesObserver)
        Mockito.verify(singleFavoritesObserver).onChanged(singleFavoriteData.getOrAwaitValueTest())
    }

    @Test
    fun `getSingleFavoriteMovie returns error`() {
        Mockito.`when`(favoritesRepository.getSingleFavoriteItem(Mockito.anyInt())).thenReturn(
            Single.error(
                dummyErrorResponse
            )
        )

        viewModel.getSingleFavoriteMovie(dummyShowsId)
        val singleFavoriteData = viewModel.singleFavoriteData

        Mockito.verify(favoritesRepository).getSingleFavoriteItem(dummyShowsId)

        favoritesRepository.getSingleFavoriteItem(dummyShowsId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        Truth.assertThat(singleFavoriteData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        singleFavoriteData.observeForever(singleFavoritesObserver)
        Mockito.verify(singleFavoritesObserver).onChanged(singleFavoriteData.getOrAwaitValueTest())
    }

//    @Test
//    fun `addFavoriteMovie returns success`() {
//        Mockito.`when`(favoritesRepository.addFavorite(Mockito.any()))
//            .thenReturn(Completable.complete())
//
//        val actionMessageData = viewModel.actionMessageData
//        Mockito.verify(favoritesRepository).addFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.addFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertComplete()
//            .assertNoErrors()
//            .dispose()
//
//        Truth.assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo("Nice taste!")
//        actionMessageData.observeForever(actionMessageObserver)
//        Mockito.verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }
//
//    @Test
//    fun `addFavoriteMovie returns error`() {
//        Mockito.`when`(favoritesRepository.addFavorite(Mockito.any())).thenReturn(
//            Completable.error(
//                dummyErrorResponse
//            )
//        )
//
//        val actionMessageData = viewModel.actionMessageData
//        Mockito.verify(favoritesRepository).addFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.addFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertNotComplete()
//            .assertNoValues()
//            .dispose()
//
//        Truth.assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo(dummyErrorMessage)
//        actionMessageData.observeForever(actionMessageObserver)
//        Mockito.verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }
//
//    @Test
//    fun `deleteFavoriteMovie returns success`() {
//        Mockito.`when`(favoritesRepository.deleteFavorite(Mockito.any()))
//            .thenReturn(Completable.complete())
//
//        val actionMessageData = viewModel.actionMessageData
//        Mockito.verify(favoritesRepository).deleteFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.deleteFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertComplete()
//            .assertNoErrors()
//            .dispose()
//
//        Truth.assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo("Nice taste!")
//        actionMessageData.observeForever(actionMessageObserver)
//        Mockito.verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }
}