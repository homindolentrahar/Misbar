package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.SearchModel
import com.homindolentrahar.misbar.domain.repositories.SearchRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Observable

class FakeSearchRepositoryAndroid : SearchRepository {
    private val pagingData = PagingData.from(DummyData.generateSearchModel())

    override fun searchItems(query: String): Observable<PagingData<SearchModel>> {
        return Observable.just(pagingData)
    }
}