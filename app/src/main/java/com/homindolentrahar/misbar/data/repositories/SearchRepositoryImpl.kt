package com.homindolentrahar.misbar.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.observable
import com.homindolentrahar.misbar.data.sources.remote.SearchRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.SearchRxPagingSource
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchRepositoryImpl(
    private val remote: SearchRemoteDataSource,
    private val pagingConfig: PagingConfig,
) : SearchRepository {
    override fun searchItems(query: String): Observable<PagingData<SearchModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { SearchRxPagingSource(remote, query) }
        )
            .observable
    }
}