package com.homindolentrahar.misbar.data.repositories

import androidx.paging.PagingData
import com.homindolentrahar.misbar.domain.models.DetailShowsModel
import com.homindolentrahar.misbar.domain.models.ShowsModel
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import com.homindolentrahar.misbar.others.constants.DummyData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class FakeShowsRepositoryAndroid : ShowsRepository {

    private val dummyShowsModel = DummyData.generateShowsModel()
    private val dummyDetailShowsModel = DummyData.generateDetailShowsModel()

    override fun getTodayAiring(): Observable<List<ShowsModel>> {
        return Observable.just(dummyShowsModel)
    }

    override fun getPopularShows(): Observable<PagingData<ShowsModel>> {
        return Observable.just(PagingData.from(dummyShowsModel))
    }

    override fun getShowsByGenre(genreId: Int): Observable<PagingData<ShowsModel>> {
        return Observable.just(PagingData.from(dummyShowsModel))
    }

    override fun getShowsDetail(showsId: Int): Single<DetailShowsModel> {
        return Single.just(dummyDetailShowsModel)
    }
}