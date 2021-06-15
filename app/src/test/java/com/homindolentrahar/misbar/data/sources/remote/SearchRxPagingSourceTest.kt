package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingSource
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRxPagingSourceTest {
    @Mock
    private lateinit var remote: SearchRemoteDataSource

    private val dummyMoviesSearchResponse = DummyData.generateSearchMoviesDtoResponse()
    private val dummyShowsSearchResponse = DummyData.generateSearchShowsDtoResponse()
    private val dummyGenresDtoResponse = DummyData.generateGenresDtoResponse()
    private val dummyMoviesSearchModel = DummyData.generateSearchMoviesModel()
    private val dummyShowsSearchModel = DummyData.generateSearchShowsModel()
    private val dummyQuery = "abab"
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Test
    fun `searchMovies loadSingle returns page when load success`() {
        val pagingSource = SearchRxPagingSource(remote, dummyQuery)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyMoviesSearchModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        Mockito.`when`(remote.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(Single.just(dummyMoviesSearchResponse))
        Mockito.`when`(remote.getMoviesGenres()).thenReturn(Single.just(dummyGenresDtoResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        Mockito.verify(remote).searchItems(1, dummyQuery)
        Mockito.verify(remote).getMoviesGenres()
        Mockito.verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(
                PagingSource.LoadResult.Page(
                    dummyMoviesSearchModel.subList(0, 10),
                    null,
                    2
                )
            )
    }

    @Test
    fun `searchMovies loadSingle returns error when load failure`() {
        val pagingSource = SearchRxPagingSource(remote, dummyQuery)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        Mockito.`when`(remote.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getMoviesGenres()).thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        Mockito.verify(remote).searchItems(1, dummyQuery)
        Mockito.verify(remote).getMoviesGenres()
        Mockito.verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }

    @Test
    fun `searchShows loadSingle returns page when load success`() {
        val pagingSource = SearchRxPagingSource(remote, dummyQuery)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyShowsSearchModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        Mockito.`when`(remote.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(Single.just(dummyShowsSearchResponse))
        Mockito.`when`(remote.getMoviesGenres()).thenReturn(Single.just(dummyGenresDtoResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        Mockito.verify(remote).searchItems(1, dummyQuery)
        Mockito.verify(remote).getMoviesGenres()
        Mockito.verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(
                PagingSource.LoadResult.Page(
                    dummyShowsSearchModel.subList(0, 10),
                    null,
                    2
                )
            )
    }

    @Test
    fun `searchShows loadSingle returns error when load failure`() {
        val pagingSource = SearchRxPagingSource(remote, dummyQuery)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        Mockito.`when`(remote.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getMoviesGenres()).thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        Mockito.verify(remote).searchItems(1, dummyQuery)
        Mockito.verify(remote).getMoviesGenres()
        Mockito.verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }

}