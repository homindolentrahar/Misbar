package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.homindolentrahar.misbar.data.sources.remote.SearchRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.SearchRxPagingSource
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: SearchRemoteDataSource

    private lateinit var repository: SearchRepository

    private val dummyMoviesSearchResponse = DummyData.generateSearchMoviesDtoResponse()
    private val dummyShowsSearchResponse = DummyData.generateSearchShowsDtoResponse()
    private val dummyMoviesSearchModel = DummyData.generateSearchMoviesModel()
    private val dummyShowsSearchModel = DummyData.generateSearchShowsModel()
    private val dummyGenresResponse = DummyData.generateGenresDtoResponse()
    private val dummyQuery = "abab"
    private val dummyPagingConfig = PagingConfig(
        pageSize = 10,
        initialLoadSize = 50,
        maxSize = 100,
        enablePlaceholders = true
    )

    @Before
    fun setup() {
        repository = SearchRepositoryImpl(remoteDataSource, dummyPagingConfig)
    }

    @Test
    fun `searchItems movies testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyMoviesSearchModel, null, 2)
        val source = SearchRxPagingSource(remoteDataSource, dummyQuery)

        Mockito.`when`(remoteDataSource.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(
                Single.just(
                    dummyMoviesSearchResponse
                )
            )
        Mockito.`when`(remoteDataSource.getMoviesGenres())
            .thenReturn(Single.just(dummyGenresResponse))
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.searchItems(dummyQuery)
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `searchItems shows testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyShowsSearchModel, null, 2)
        val source = SearchRxPagingSource(remoteDataSource, dummyQuery)

        Mockito.`when`(remoteDataSource.searchItems(Mockito.anyInt(), Mockito.anyString()))
            .thenReturn(
                Single.just(
                    dummyShowsSearchResponse
                )
            )
        Mockito.`when`(remoteDataSource.getMoviesGenres())
            .thenReturn(Single.just(dummyGenresResponse))
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.searchItems(dummyQuery)
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

}