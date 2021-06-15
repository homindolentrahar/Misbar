package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.homindolentrahar.misbar.data.sources.remote.MoviesRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.MoviesRxPagingSource
import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: MoviesRemoteDataSource

    private lateinit var repository: MoviesRepository

    private val dummyMoviesResponse = DummyData.generateMoviesDtoResponse()
    private val dummyMoviesModel = DummyData.generateMoviesModel()
    private val dummyGenresResponse = DummyData.generateGenresDtoResponse()
    private val dummyGenresResponseEmpty = DummyData.generateGenresDtoResponseEmpty()
    private val dummyDetailMoviesResponse = DummyData.generateDetailMoviesDto()
    private val dummyDetailMoviesModel = DummyData.generateDetailMoviesModel()
    private val dummyGenreId = 16
    private val dummyMoviesId = 123
    private val dummyPagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 50,
        maxSize = 100,
        enablePlaceholders = true
    )
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        repository = MoviesRepositoryImpl(remoteDataSource, dummyPagingConfig)
    }

    //    Now Playing
    @Test
    fun `getNowPlaying returns correct response`() {
        `when`(remoteDataSource.getNowPlaying()).thenReturn(Single.just(dummyMoviesResponse))
        `when`(remoteDataSource.getMoviesGenres()).thenReturn(Single.just(dummyGenresResponse))

        val observable = repository.getNowPlaying()

        verify(remoteDataSource).getNowPlaying()
        verify(remoteDataSource).getMoviesGenres()

        observable
            .test()
            .await()
            .assertValue(dummyMoviesModel)
            .assertValue { it.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getNowPlaying returns error`() {
        `when`(remoteDataSource.getNowPlaying()).thenReturn(Single.error(dummyErrorResponse))
        `when`(remoteDataSource.getMoviesGenres()).thenReturn(Single.error(dummyErrorResponse))

        val observable = repository.getNowPlaying()

        verify(remoteDataSource).getNowPlaying()
        verify(remoteDataSource).getMoviesGenres()

        observable
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `getNowPlaying returns correct response without genres`() {
        `when`(remoteDataSource.getNowPlaying()).thenReturn(Single.just(dummyMoviesResponse))
        `when`(remoteDataSource.getMoviesGenres()).thenReturn(Single.just(dummyGenresResponseEmpty))

        val observable = repository.getNowPlaying()

        verify(remoteDataSource).getNowPlaying()
        verify(remoteDataSource).getMoviesGenres()

        observable
            .test()
            .await()
            .assertValue(dummyMoviesModel)
            .assertValue { it[0].genres.isEmpty() }
            .assertNoErrors()
            .dispose()
    }

    //    Popular Movies
    @Test
    fun `getPopularMovies testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyMoviesModel, null, 2)
        val source = MoviesRxPagingSource(remoteDataSource)

        `when`(remoteDataSource.getPopularMovies(anyInt())).thenReturn(
            Single.just(
                dummyMoviesResponse
            )
        )
        `when`(remoteDataSource.getMoviesGenres()).thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.getPopularMovies()
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    //    Movies by Genre
    @Test
    fun `getMoviesByGenre testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyMoviesModel, null, 2)
        val source = MoviesRxPagingSource(remoteDataSource, dummyGenreId)

        `when`(remoteDataSource.getMoviesByGenre(anyInt(), anyInt())).thenReturn(
            Single.just(
                dummyMoviesResponse
            )
        )
        `when`(remoteDataSource.getMoviesGenres()).thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.getMoviesByGenre(dummyGenreId)
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    //    Movies Detail
    @Test
    fun `getDetailMovies returns correct response`() {
        `when`(remoteDataSource.getMoviesDetail(anyInt())).thenReturn(
            Single.just(
                dummyDetailMoviesResponse
            )
        )

        val observableResponse = repository.getMoviesDetail(dummyMoviesId)

        verify(remoteDataSource).getMoviesDetail(dummyMoviesId)

        observableResponse
            .test()
            .await()
            .assertValue(dummyDetailMoviesModel)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getDetailMovies returns error`() {
        `when`(remoteDataSource.getMoviesDetail(anyInt())).thenReturn(
            Single.error(
                dummyErrorResponse
            )
        )

        val observableResponse = repository.getMoviesDetail(dummyMoviesId)

        verify(remoteDataSource).getMoviesDetail(dummyMoviesId)

        observableResponse
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()
    }

}