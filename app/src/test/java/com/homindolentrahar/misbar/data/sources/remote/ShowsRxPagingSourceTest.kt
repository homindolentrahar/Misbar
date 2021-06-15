package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingSource
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsRxPagingSourceTest {
    @Mock
    private lateinit var remote: ShowsRemoteDataSource

    private val dummyShowsDtoResponse = DummyData.generateShowsDtoResponse()
    private val dummyGenresDtoResponse = DummyData.generateGenresDtoResponse()
    private val dummyShowsModel = DummyData.generateShowsModel()
    private val dummyGenreId = DummyData.generateGenresDtoResponse().genres!![0].id!!
    private val emptyGenreId = -1
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Test
    fun `getShowsPopular loadSingle returns page when load success`() {
        val pagingSource = ShowsRxPagingSource(remote, emptyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyShowsModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        Mockito.`when`(remote.getPopularShows(anyInt()))
            .thenReturn(Single.just(dummyShowsDtoResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getPopularShows(1)
        verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Page(dummyShowsModel.subList(0, 10), null, 2))
    }

    @Test
    fun `getShowsPopular loadSingle returns error when load failure`() {
        val pagingSource = ShowsRxPagingSource(remote, emptyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        Mockito.`when`(remote.getPopularShows(anyInt()))
            .thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getPopularShows(anyInt())
        verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }

    @Test
    fun `getShowsByGenre loadSingle returns page when load success`() {
        val pagingSource = ShowsRxPagingSource(remote, dummyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyShowsModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        Mockito.`when`(remote.getShowsByGenre(anyInt(), anyInt()))
            .thenReturn(Single.just(dummyShowsDtoResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getShowsByGenre(1, dummyGenreId)
        verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Page(dummyShowsModel.subList(0, 10), null, 2))
    }

    @Test
    fun `getShowsByGenres loadSingle returns error when load failure`() {
        val pagingSource = ShowsRxPagingSource(remote, dummyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        Mockito.`when`(remote.getShowsByGenre(anyInt(), anyInt()))
            .thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remote.getShowsGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getShowsByGenre(anyInt(), anyInt())
        verify(remote).getShowsGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }
}