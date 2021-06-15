package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.homindolentrahar.misbar.data.sources.remote.ShowsRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.ShowsRxPagingSource
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowsRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: ShowsRemoteDataSource

    private lateinit var repository: ShowsRepository

    private val dummyShowsResponse = DummyData.generateShowsDtoResponse()
    private val dummyShowsModel = DummyData.generateShowsModel()
    private val dummyGenresResponse = DummyData.generateGenresDtoResponse()
    private val dummyGenresResponseEmpty = DummyData.generateGenresDtoResponseEmpty()
    private val dummyDetailShowsResponse = DummyData.generateDetailShowsDto()
    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()
    private val dummyGenreId = 16
    private val dummyShowsId = 123
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
        repository = ShowsRepositoryImpl(remoteDataSource, dummyPagingConfig)
    }

    //    Now Playing
    @Test
    fun `getTodayAiring returns correct response`() {
        Mockito.`when`(remoteDataSource.getTodayAiring())
            .thenReturn(Single.just(dummyShowsResponse))
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponse))

        val observableResponse = repository.getTodayAiring()

        Mockito.verify(remoteDataSource).getTodayAiring()
        Mockito.verify(remoteDataSource).getShowsGenres()

        observableResponse
            .test()
            .await()
            .assertValue(dummyShowsModel)
            .assertValue { it.isNotEmpty() }
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getTodayAiring returns error`() {
        Mockito.`when`(remoteDataSource.getTodayAiring())
            .thenReturn(Single.error(dummyErrorResponse))
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.error(dummyErrorResponse))

        val observableResponse = repository.getTodayAiring()

        Mockito.verify(remoteDataSource).getTodayAiring()
        Mockito.verify(remoteDataSource).getShowsGenres()

        observableResponse
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()
    }

    @Test
    fun `getTodayAiring returns correct response without genres`() {
        Mockito.`when`(remoteDataSource.getTodayAiring())
            .thenReturn(Single.just(dummyShowsResponse))
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponseEmpty))

        val observableResponse = repository.getTodayAiring()

        Mockito.verify(remoteDataSource).getTodayAiring()
        Mockito.verify(remoteDataSource).getShowsGenres()

        observableResponse
            .test()
            .await()
            .assertValue(dummyShowsModel)
            .assertValue { it[0].genres.isEmpty() }
            .assertNoErrors()
            .dispose()
    }

    //    Popular Shows
    @Test
    fun `getPopularShows testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyShowsModel, null, 2)
        val source = ShowsRxPagingSource(remoteDataSource)

        Mockito.`when`(remoteDataSource.getPopularShows(Mockito.anyInt())).thenReturn(
            Single.just(
                dummyShowsResponse
            )
        )
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.getPopularShows()
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    //    Shows by Genre
    @Test
    fun `getShowsByGenre testing`() {
        val pagingParams = PagingSource.LoadParams.Refresh<Int>(null, 10, false)
        val result = PagingSource.LoadResult.Page(dummyShowsModel, null, 2)
        val source = ShowsRxPagingSource(remoteDataSource, dummyGenreId)

        Mockito.`when`(remoteDataSource.getShowsByGenre(Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(
                Single.just(
                    dummyShowsResponse
                )
            )
        Mockito.`when`(remoteDataSource.getShowsGenres())
            .thenReturn(Single.just(dummyGenresResponse))

        source.loadSingle(pagingParams)
            .test()
            .await()
            .assertValue(result)
            .dispose()

        repository.getShowsByGenre(dummyGenreId)
            .test()
            .assertValueCount(1)
            .assertNoErrors()
            .dispose()
    }

    //    Shows Detail
    @Test
    fun `getDetailShows returns correct response`() {
        Mockito.`when`(remoteDataSource.getShowsDetail(Mockito.anyInt())).thenReturn(
            Single.just(
                dummyDetailShowsResponse
            )
        )

        val observableResponse = repository.getShowsDetail(dummyShowsId)

        Mockito.verify(remoteDataSource).getShowsDetail(dummyShowsId)

        observableResponse
            .test()
            .await()
            .assertValue(dummyDetailShowsModel)
            .assertNoErrors()
            .dispose()
    }

    @Test
    fun `getDetailShows returns error`() {
        Mockito.`when`(remoteDataSource.getShowsDetail(Mockito.anyInt()))
            .thenReturn(Single.error(dummyErrorResponse))

        val observableResponse = repository.getShowsDetail(dummyShowsId)

        Mockito.verify(remoteDataSource).getShowsDetail(dummyShowsId)

        observableResponse
            .test()
            .await()
            .assertError { it.message == "Cannot retrieve data" }
            .assertNoValues()
            .dispose()
    }
}