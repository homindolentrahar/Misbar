package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RetrofitSearchRemoteDataSourceTest {

    private lateinit var remoteDataSource: SearchRemoteDataSource

    @Mock
    private lateinit var tmdbApi: TMDBApi

    private val dummySearchResponse = DummyData.generateSearchMoviesDtoResponse()
    private val dummyPage = 1
    private val dummyQuery = "abab"
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Before
    fun setup(){
        remoteDataSource = RetrofitSearchRemoteDataSource(tmdbApi)
    }

    @Test
    fun `searchItems returns correct response`() {
        Mockito.`when`(tmdbApi.searchItems(anyInt(), anyString()))
            .thenReturn(Single.just(dummySearchResponse))

        val single = remoteDataSource.searchItems(dummyPage,dummyQuery)

        Mockito.verify(tmdbApi).searchItems(dummyPage, dummyQuery)

        single
            .test()
            .await()
            .assertValue(dummySearchResponse)
            .assertValue { it.results!!.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `searchItems returns error`() {
        Mockito.`when`(tmdbApi.searchItems(anyInt(), anyString()))
            .thenReturn(Single.error(dummyErrorResponse))

        val single = remoteDataSource.searchItems(dummyPage, dummyQuery)

        Mockito.verify(tmdbApi).searchItems(dummyPage, dummyQuery)

        single
            .test()
            .await()
            .assertNoValues()
            .assertError { it.message == "Cannot retrieve data" }
            .dispose()
    }

}