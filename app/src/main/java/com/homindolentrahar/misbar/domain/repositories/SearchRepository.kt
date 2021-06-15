package com.homindolentrahar.misbar.domain.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.SearchModel
import io.reactivex.rxjava3.core.Observable

interface SearchRepository {
    fun searchItems(query: String): Observable<PagingData<SearchModel>>
}