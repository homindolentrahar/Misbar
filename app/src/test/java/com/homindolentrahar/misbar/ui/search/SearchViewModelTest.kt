package com.homindolentrahar.misbar.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.homindolentrahar.misbar.RxImmediateSchedulerRule
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import com.homindolentrahar.misbar.others.wrappers.Resource
import com.homindolentrahar.misbar.others.wrappers.Status
import com.homindolentrahar.misbar.utils.getOrAwaitValueTest
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val rxImmediateRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var repository: SearchRepository

    @Mock
    private lateinit var searchFragmentObserver: Observer<Resource<PagingData<SearchModel>>>

    private lateinit var viewModel: SearchViewModel

    private val dummySearchModel = DummyData.generateSearchMoviesModel()
    private val dummyPagingData = PagingData.from(dummySearchModel)
    private val dummyQuery = "abab"

    @Before
    fun setup() {
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `getSearchFragmentData testing`() {
        Mockito.`when`(repository.searchItems(anyString()))
            .thenReturn(Observable.just(dummyPagingData))

        viewModel.getSearchFragmentDataTest(dummyQuery)
        val searchFragmentData = viewModel.searchFragmentData

        Mockito.verify(repository).searchItems(dummyQuery)

        repository.searchItems(dummyQuery)
            .test()
            .await()
            .assertValue(dummyPagingData)
            .assertNoErrors()
            .dispose()

        Truth.assertThat(searchFragmentData.getOrAwaitValueTest().status).isEqualTo(Status.Success)
        searchFragmentData.observeForever(searchFragmentObserver)
        Mockito.verify(searchFragmentObserver).onChanged(searchFragmentData.getOrAwaitValueTest())
    }

}