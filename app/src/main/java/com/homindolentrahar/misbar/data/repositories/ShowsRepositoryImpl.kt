package com.homindolentrahar.misbar.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.homindolentrahar.misbar.data.sources.remote.ShowsRemoteDataSource
import com.homindolentrahar.misbar.data.sources.remote.ShowsRxPagingSource
import com.homindolentrahar.misbar.data.sources.remote.dtos.ShowsDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
import com.homindolentrahar.misbar.domain.models.DetailShowsModel
import com.homindolentrahar.misbar.domain.models.ShowsModel
import com.homindolentrahar.misbar.domain.models.GenresModel
import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ShowsRepositoryImpl(
    private val remote: ShowsRemoteDataSource,
    private val pagingConfig: PagingConfig
) : ShowsRepository {
    override fun getTodayAiring(): Observable<List<ShowsModel>> {
        return getShowsModelWithGenres(remote.getTodayAiring())
    }

    override fun getPopularShows(): Observable<PagingData<ShowsModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ShowsRxPagingSource(remote) }
        ).observable
    }

    override fun getShowsByGenre(genreId: Int): Observable<PagingData<ShowsModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ShowsRxPagingSource(remote, genreId) }
        ).observable
    }

    override fun getShowsDetail(showsId: Int): Single<DetailShowsModel> {
        return remote.getShowsDetail(showsId)
            .map { response -> response.toModel() }
    }

    private fun getShowsModelWithGenres(single: Single<ShowsDtoResponse>): Observable<List<ShowsModel>> {
        return Observable.zip(
            single.toObservable(),
            remote.getShowsGenres().toObservable(),
            { dtoResponse, genresDtoResponse ->
                dtoResponse.results?.map { showsDto ->
                    showsDto.toModel().apply {
                        this.genres = genresDtoResponse.genres?.filter { genresDto ->
                            showsDto.genreIds!!.contains(genresDto.id)
                        }?.take(3)?.toModels() as List<GenresModel>
                    }
                }
            }
        )
    }
}