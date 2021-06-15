package com.homindolentrahar.misbar.domain.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.DetailShowsModel
import com.homindolentrahar.misbar.domain.models.ShowsModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface ShowsRepository {
    fun getTodayAiring(): Observable<List<ShowsModel>>

    fun getPopularShows(): Observable<PagingData<ShowsModel>>

    fun getShowsByGenre(genreId: Int): Observable<PagingData<ShowsModel>>

    fun getShowsDetail(showsId: Int): Single<DetailShowsModel>
}