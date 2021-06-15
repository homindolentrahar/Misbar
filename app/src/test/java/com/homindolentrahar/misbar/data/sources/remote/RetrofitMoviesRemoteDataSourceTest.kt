package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitMoviesRemoteDataSourceTest {

    private lateinit var remoteDataSource: MoviesRemoteDataSource

    @Mock
    private lateinit var tmdbApi: TMDBApi

    private val dummyMoviesResponse = DummyData.generateMoviesDtoResponse()
    private val dummyDetailMoviesResponse = DummyData.generateDetailMoviesDto()
    private val dummyGenresResponse = DummyData.generateGenresDtoResponse()
    private val dummyGenreId = 123
    private val dummyMovieId = 4321
    private val dummyPage = 1

    @Before
    fun setup() {
        remoteDataSource = RetrofitMoviesRemoteDataSource(tmdbApi)
    }

    @Test
    fun `getNowPlaying returns correct response`() {
        `when`(tmdbApi.getMoviesNowPlaying()).thenReturn(Single.just(dummyMoviesResponse))

        val single = remoteDataSource.getNowPlaying()

        verify(tmdbApi).getMoviesNowPlaying()

        single
            .test()
            .await()
            .assertValue(dummyMoviesResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getNowPlaying returns error`() {
        `when`(tmdbApi.getMoviesNowPlaying()).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getNowPlaying()

        verify(tmdbApi).getMoviesNowPlaying()

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getPopularMovies returns correct response`() {
        `when`(tmdbApi.getMoviesPopular(anyInt())).thenReturn(Single.just(dummyMoviesResponse))

        val single = remoteDataSource.getPopularMovies(dummyPage)

        verify(tmdbApi).getMoviesPopular(dummyPage)

        single
            .test()
            .await()
            .assertValue(dummyMoviesResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getPopularMovies returns error`() {
        `when`(tmdbApi.getMoviesPopular(anyInt())).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getPopularMovies(dummyPage)

        verify(tmdbApi).getMoviesPopular(dummyPage)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getMoviesByGenre returns correct response`() {
        `when`(tmdbApi.getMoviesByGenre(anyInt(), anyInt())).thenReturn(
            Single.just(
                dummyMoviesResponse
            )
        )

        val single = remoteDataSource.getMoviesByGenre(dummyPage, dummyGenreId)

        verify(tmdbApi).getMoviesByGenre(dummyPage, dummyGenreId)

        single
            .test()
            .await()
            .assertValue(dummyMoviesResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getMoviesByGenre returns error`() {
        `when`(
            tmdbApi.getMoviesByGenre(
                anyInt(),
                anyInt()
            )
        ).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getMoviesByGenre(dummyPage, dummyGenreId)

        verify(tmdbApi).getMoviesByGenre(dummyPage, dummyGenreId)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getMoviesDetail returns correct response`() {
        `when`(tmdbApi.getMoviesDetail(anyInt())).thenReturn(Single.just(dummyDetailMoviesResponse))

        val single = remoteDataSource.getMoviesDetail(dummyMovieId)

        verify(tmdbApi).getMoviesDetail(dummyMovieId)

        single
            .test()
            .await()
            .assertValue(dummyDetailMoviesResponse)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getMoviesDetail returns error`() {
        `when`(tmdbApi.getMoviesDetail(anyInt())).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getMoviesDetail(dummyMovieId)

        verify(tmdbApi).getMoviesDetail(dummyMovieId)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getMoviesGenres returns correct response`() {
        `when`(tmdbApi.getMoviesGenres()).thenReturn(Single.just(dummyGenresResponse))

        val single = remoteDataSource.getMoviesGenres()

        verify(tmdbApi).getMoviesGenres()

        single
            .test()
            .await()
            .assertValue(dummyGenresResponse)
            .assertValue { it.genres!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getMoviesGenres returns error`() {
        `when`(tmdbApi.getMoviesGenres()).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getMoviesGenres()

        verify(tmdbApi).getMoviesGenres()

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }
}