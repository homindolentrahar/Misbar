package com.homindolentrahar.misbar.data.sources.remote

import androidx.paging.PagingSource
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesRxPagingSourceTest {

    @Mock
    private lateinit var remote: MoviesRemoteDataSource

    private val dummyMoviesDtoResponse = DummyData.generateMoviesDtoResponse()
    private val dummyGenresDtoResponse = DummyData.generateGenresDtoResponse()
    private val dummyMoviesModel = DummyData.generateMoviesModel()
    private val dummyGenreId = DummyData.generateGenresDtoResponse().genres!![0].id!!
    private val emptyGenreId = -1
    private val dummyErrorResponse = Throwable("Cannot retrieve data")

    @Test
    fun `getMoviesPopular loadSingle returns page when load success`() {
        val pagingSource = MoviesRxPagingSource(remote, emptyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyMoviesModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        `when`(remote.getPopularMovies(anyInt())).thenReturn(Single.just(dummyMoviesDtoResponse))
        `when`(remote.getMoviesGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getPopularMovies(1)
        verify(remote).getMoviesGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Page(dummyMoviesModel.subList(0, 10), null, 2))
    }

    @Test
    fun `getMoviesPopular loadSingle returns error when load failure`() {
        val pagingSource = MoviesRxPagingSource(remote, emptyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        `when`(remote.getPopularMovies(anyInt())).thenReturn(Single.error(dummyErrorResponse))
        `when`(remote.getMoviesGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getPopularMovies(1)
        verify(remote).getMoviesGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }

    @Test
    fun `getMoviesByGenre loadSingle returns page when load success`() {
        val pagingSource = MoviesRxPagingSource(remote, dummyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            dummyMoviesModel.size,
            false
        ) as PagingSource.LoadParams<Int>

        `when`(remote.getMoviesByGenre(anyInt(), anyInt())).thenReturn(
            Single.just(
                dummyMoviesDtoResponse
            )
        )
        `when`(remote.getMoviesGenres()).thenReturn(Single.just(dummyGenresDtoResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getMoviesByGenre(1, dummyGenreId)
        verify(remote).getMoviesGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Page(dummyMoviesModel.subList(0, 10), null, 2))
    }

    @Test
    fun `getMoviesByGenres loadSingle returns error when load failure`() {
        val pagingSource = MoviesRxPagingSource(remote, dummyGenreId)
        val params = PagingSource.LoadParams.Refresh<Int>(
            null,
            0,
            false
        )

        `when`(remote.getMoviesByGenre(anyInt(), anyInt())).thenReturn(
            Single.error(
                dummyErrorResponse
            )
        )
        `when`(remote.getMoviesGenres()).thenReturn(Single.error(dummyErrorResponse))

        val single = pagingSource.loadSingle(params)

        verify(remote).getMoviesByGenre(1, dummyGenreId)
        verify(remote).getMoviesGenres()

        single
            .test()
            .await()
            .assertValueCount(1)
            .assertValue(PagingSource.LoadResult.Error(dummyErrorResponse))
    }

}