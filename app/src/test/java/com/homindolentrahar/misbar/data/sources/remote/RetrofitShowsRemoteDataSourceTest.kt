package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitShowsRemoteDataSourceTest {

    private lateinit var remoteDataSource: ShowsRemoteDataSource

    @Mock
    private lateinit var tmdbApi: TMDBApi

    private val dummyShowsResponse = DummyData.generateShowsDtoResponse()
    private val dummyDetailShowsResponse = DummyData.generateDetailShowsDto()
    private val dummyGenresResponse = DummyData.generateGenresDtoResponse()
    private val dummyGenreId = 123
    private val dummyShowId = 4321
    private val dummyPage = 1

    @Before
    fun setup() {
        remoteDataSource = RetrofitShowsRemoteDataSource(tmdbApi)
    }

    @Test
    fun `getTodayAiring returns correct response`() {
        `when`(tmdbApi.getShowsTodayAiring()).thenReturn(Single.just(dummyShowsResponse))

        val single = remoteDataSource.getTodayAiring()

        verify(tmdbApi).getShowsTodayAiring()

        single
            .test()
            .await()
            .assertValue(dummyShowsResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getTodayAiring returns error`() {
        `when`(tmdbApi.getShowsTodayAiring()).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getTodayAiring()

        verify(tmdbApi).getShowsTodayAiring()

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getPopularShows returns correct response`() {
        `when`(tmdbApi.getShowsPopular(anyInt())).thenReturn(Single.just(dummyShowsResponse))

        val single = remoteDataSource.getPopularShows(dummyPage)

        verify(tmdbApi).getShowsPopular(dummyPage)

        single
            .test()
            .await()
            .assertValue(dummyShowsResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getPopularShows returns error`() {
        `when`(tmdbApi.getShowsPopular(anyInt())).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getPopularShows(dummyPage)

        verify(tmdbApi).getShowsPopular(dummyPage)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getShowsByGenre returns correct response`() {
        `when`(tmdbApi.getShowsByGenre(anyInt(), anyInt())).thenReturn(
            Single.just(
                dummyShowsResponse
            )
        )

        val single = remoteDataSource.getShowsByGenre(dummyPage, dummyGenreId)

        verify(tmdbApi).getShowsByGenre(dummyPage, dummyGenreId)

        single
            .test()
            .await()
            .assertValue(dummyShowsResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getShowsByGenre returns error`() {
        `when`(
            tmdbApi.getShowsByGenre(
                anyInt(),
                anyInt()
            )
        ).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getShowsByGenre(dummyPage, dummyGenreId)

        verify(tmdbApi).getShowsByGenre(dummyPage, dummyGenreId)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getShowsDetail returns correct response`() {
        `when`(tmdbApi.getShowsDetail(anyInt())).thenReturn(Single.just(dummyDetailShowsResponse))

        val single = remoteDataSource.getShowsDetail(dummyShowId)

        verify(tmdbApi).getShowsDetail(dummyShowId)

        single
            .test()
            .await()
            .assertValue(dummyDetailShowsResponse)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getShowsDetail returns error`() {
        `when`(tmdbApi.getShowsDetail(anyInt())).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getShowsDetail(dummyShowId)

        verify(tmdbApi).getShowsDetail(dummyShowId)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

    @Test
    fun `getShowsGenres returns correct response`() {
        `when`(tmdbApi.getShowsGenres()).thenReturn(Single.just(dummyGenresResponse))

        val single = remoteDataSource.getShowsGenres()

        verify(tmdbApi).getShowsGenres()

        single
            .test()
            .await()
            .assertValue(dummyGenresResponse)
            .assertValue { it.genres!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getShowsGenres returns error`() {
        `when`(tmdbApi.getShowsGenres()).thenReturn(Single.error(Throwable("Cannot retrieve data")))

        val single = remoteDataSource.getShowsGenres()

        verify(tmdbApi).getShowsGenres()

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }
}