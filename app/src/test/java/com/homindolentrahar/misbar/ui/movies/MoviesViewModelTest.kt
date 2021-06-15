package com.homindolentrahar.misbar.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.homindolentrahar.misbar.RxImmediateSchedulerRule
import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
import com.homindolentrahar.misbar.domain.models.FavoritesModel
import com.homindolentrahar.misbar.domain.models.MoviesFragmentData
import com.homindolentrahar.misbar.domain.models.MoviesModel
import com.homindolentrahar.misbar.domain.repositories.FavoritesRepository
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    private lateinit var favoritesRepository: FavoritesRepository

    @Mock
    private lateinit var moviesFragmentObserver: Observer<Resource<MoviesFragmentData>>

    @Mock
    private lateinit var detailMoviesObserver: Observer<Resource<DetailMoviesModel>>

    @Mock
    private lateinit var singleFavoritesObserver: Observer<Resource<FavoritesModel>>

    private lateinit var viewModel: MoviesViewModel

    private val dummyMoviesModel = DummyData.generateMoviesModel()
    private val dummySingleFavoritesModel = DummyData.generateFavoritesModel()[0]
    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()
    private val dummyMoviesId = 123456
    private val dummyGenreId = 4321
    private val dummyErrorResponse = Throwable("Cannot retrieve data")
    private val dummyPagingData = PagingData.from(dummyMoviesModel)
    private val dummyMoviesFragmentData =
        MoviesFragmentData(dummyMoviesModel.take(5), dummyPagingData)

    @Before
    fun setup() {
        viewModel = MoviesViewModel(repository, favoritesRepository)
    }

    //    Movies Fragment Data
    @Test
    fun `getMoviesFragmentData returns success`() {
        `when`(repository.getNowPlaying()).thenReturn(Observable.just(dummyMoviesModel))
        `when`(repository.getPopularMovies()).thenReturn(Observable.just(dummyPagingData))

        viewModel.getMoviesFragmentData()
        val moviesFragmentData = viewModel.moviesFragmentData

        verify(repository).getNowPlaying()
        verify(repository).getPopularMovies()

        Observable.zip(
            repository.getNowPlaying(),
            repository.getPopularMovies(),
            { now, popular -> MoviesFragmentData(now.take(5), popular) }
        )
            .test()
            .await()
            .assertValue(dummyMoviesFragmentData)
            .assertValue { it.listData.isNotEmpty() }
            .assertNoErrors()
            .dispose()

        assertThat(moviesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        moviesFragmentData.observeForever(moviesFragmentObserver)
        verify(moviesFragmentObserver).onChanged(moviesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getMoviesFragmentData returns error`() {
        `when`(repository.getNowPlaying()).thenReturn(Observable.error(dummyErrorResponse))
        `when`(repository.getPopularMovies()).thenReturn(Observable.error(dummyErrorResponse))

        viewModel.getMoviesFragmentData()
        val moviesFragmentData = viewModel.moviesFragmentData

        verify(repository).getNowPlaying()
        verify(repository).getPopularMovies()

        Observable.zip(
            repository.getNowPlaying(),
            repository.getPopularMovies(),
            { _, _ -> emptyList<MoviesModel>() }
        )
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        assertThat(moviesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        moviesFragmentData.observeForever(moviesFragmentObserver)
        verify(moviesFragmentObserver).onChanged(moviesFragmentData.getOrAwaitValueTest())
    }

    //    Movies by Genre
    @Test
    fun `getMoviesByGenre returns success`() {
        `when`(repository.getMoviesByGenre(anyInt())).thenReturn(Observable.just(dummyPagingData))

        viewModel.getMoviesByGenre(dummyGenreId)
        val moviesFragmentData = viewModel.moviesByGenre

        verify(repository).getMoviesByGenre(dummyGenreId)

        repository.getMoviesByGenre(dummyGenreId)
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        assertThat(moviesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        moviesFragmentData.observeForever(moviesFragmentObserver)
        verify(moviesFragmentObserver).onChanged(moviesFragmentData.getOrAwaitValueTest())
    }

    @Test
    fun `getMoviesByGenre returns error`() {
        `when`(repository.getMoviesByGenre(anyInt())).thenReturn(Observable.error(dummyErrorResponse))

        viewModel.getMoviesByGenre(dummyGenreId)
        val moviesFragmentData = viewModel.moviesByGenre

        verify(repository).getMoviesByGenre(dummyGenreId)

        repository.getMoviesByGenre(dummyGenreId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        assertThat(moviesFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        moviesFragmentData.observeForever(moviesFragmentObserver)
        verify(moviesFragmentObserver).onChanged(moviesFragmentData.getOrAwaitValueTest())
    }

    //    Movies Detail
    @Test
    fun `getMoviesDetail returns success`() {
        `when`(repository.getMoviesDetail(anyInt())).thenReturn(Single.just(dummyDetailMoviesModel))

        viewModel.getMoviesDetail(dummyMoviesId)
        val detailMoviesData = viewModel.detailMoviesData

        verify(repository).getMoviesDetail(dummyMoviesId)

        repository.getMoviesDetail(dummyMoviesId)
            .test()
            .await()
            .assertValue(dummyDetailMoviesModel)
            .assertNoErrors()
            .dispose()

        assertThat(detailMoviesData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        detailMoviesData.observeForever(detailMoviesObserver)
        verify(detailMoviesObserver).onChanged(detailMoviesData.getOrAwaitValueTest())
    }

    @Test
    fun `getMoviesDetail returns error`() {
        `when`(repository.getMoviesDetail(anyInt())).thenReturn(Single.error(dummyErrorResponse))

        viewModel.getMoviesDetail(dummyMoviesId)
        val detailMoviesData = viewModel.detailMoviesData

        verify(repository).getMoviesDetail(dummyMoviesId)

        repository.getMoviesDetail(dummyMoviesId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        assertThat(detailMoviesData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        detailMoviesData.observeForever(detailMoviesObserver)
        verify(detailMoviesObserver).onChanged(detailMoviesData.getOrAwaitValueTest())
    }

    @Test
    fun `getSingleFavoriteMovie returns correct response`() {
        `when`(favoritesRepository.getSingleFavoriteItem(anyInt())).thenReturn(
            Single.just(
                dummySingleFavoritesModel
            )
        )

        viewModel.getSingleFavoriteMovie(dummyMoviesId)
        val singleFavoriteData = viewModel.singleFavoriteData

        verify(favoritesRepository).getSingleFavoriteItem(dummyMoviesId)

        favoritesRepository.getSingleFavoriteItem(dummyMoviesId)
            .test()
            .await()
            .assertValue(dummySingleFavoritesModel)
            .assertNoErrors()
            .dispose()

        assertThat(singleFavoriteData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        singleFavoriteData.observeForever(singleFavoritesObserver)
        verify(singleFavoritesObserver).onChanged(singleFavoriteData.getOrAwaitValueTest())
    }

    @Test
    fun `getSingleFavoriteMovie returns error`() {
        `when`(favoritesRepository.getSingleFavoriteItem(anyInt())).thenReturn(
            Single.error(
                dummyErrorResponse
            )
        )

        viewModel.getSingleFavoriteMovie(dummyMoviesId)
        val singleFavoriteData = viewModel.singleFavoriteData

        verify(favoritesRepository).getSingleFavoriteItem(dummyMoviesId)

        favoritesRepository.getSingleFavoriteItem(dummyMoviesId)
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()

        assertThat(singleFavoriteData.getOrAwaitValueTest().status).isEqualTo(Status.Error)
        singleFavoriteData.observeForever(singleFavoritesObserver)
        verify(singleFavoritesObserver).onChanged(singleFavoriteData.getOrAwaitValueTest())
    }

//    @Test
//    fun `addFavoriteMovie returns success`() {
//        val mockFavoritesModel = mock(FavoritesModel::class.java)
//        `when`(favoritesRepository.addFavorite(mockFavoritesModel)).thenReturn(Completable.complete())
////        `when`(favoritesRepository.getAllFavoriteItems()).thenReturn(Observable.just(PagingData.from(dummyF)))
//
//        viewModel.addFavoriteMovie(dummyDetailMoviesModel)
//        val actionMessageData = viewModel.actionMessageData
//
//        verify(favoritesRepository).addFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.addFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertComplete()
//            .assertNoErrors()
//            .dispose()
//
//        assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo("Nice taste!")
//        actionMessageData.observeForever(actionMessageObserver)
//        verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
////        `when`(favoritesRepository.addFavorite(any())).thenReturn(Completable.complete())
////        viewModel.getSingleFavoriteMovie(dummyMoviesId)
////        viewModel.toggleFavorite(dummyDetailMoviesModel)
////
////        val actionMessageData = viewModel.actionMessageData
////        verify(favoritesRepository).addFavorite(dummySingleFavoritesModel)
////
////        favoritesRepository.addFavorite(dummySingleFavoritesModel)
////            .test()
////            .await()
////            .assertComplete()
////            .assertNoErrors()
////            .dispose()
////
////        assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo("Nice taste!")
////        actionMessageData.observeForever(actionMessageObserver)
////        verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }
//
//    @Test
//    fun `addFavoriteMovie returns error`() {
//        `when`(favoritesRepository.addFavorite(any())).thenReturn(
//            Completable.error(
//                dummyErrorResponse
//            )
//        )
//
//        val actionMessageData = viewModel.actionMessageData
//        verify(favoritesRepository).addFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.addFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertNotComplete()
//            .assertNoValues()
//            .dispose()
//
//        assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo(dummyErrorMessage)
//        actionMessageData.observeForever(actionMessageObserver)
//        verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }
//
//    @Test
//    fun `deleteFavoriteMovie returns success`() {
//        `when`(favoritesRepository.deleteFavorite(any())).thenReturn(Completable.complete())
//
//        val actionMessageData = viewModel.actionMessageData
//        verify(favoritesRepository).deleteFavorite(dummySingleFavoritesModel)
//
//        favoritesRepository.deleteFavorite(dummySingleFavoritesModel)
//            .test()
//            .await()
//            .assertComplete()
//            .assertNoErrors()
//            .dispose()
//
//        assertThat(actionMessageData.getOrAwaitValueTest()).isEqualTo("Nice taste!")
//        actionMessageData.observeForever(actionMessageObserver)
//        verify(actionMessageObserver).onChanged(actionMessageData.getOrAwaitValueTest())
//    }


}