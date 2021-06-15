//package com.homindolentrahar.misbar.data.repositories
//
//import com.homindolentrahar.misbar.data.sources.remote.ShowsRemoteDataSource
//import com.homindolentrahar.misbar.data.sources.remote.dtos.ShowsDtoResponse
//import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
//import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
//import com.homindolentrahar.misbar.domain.models.DetailShowsModel
//import com.homindolentrahar.misbar.domain.models.ShowsModel
//import com.homindolentrahar.misbar.domain.models.GenresModel
//import com.homindolentrahar.misbar.domain.repositories.ShowsRepository
//import io.reactivex.rxjava3.core.Observable
//import io.reactivex.rxjava3.core.Single
//
//class FakeShowsRepository(private val remote: ShowsRemoteDataSource) : ShowsRepository {
//
//    override fun getTodayAiring(): Observable<List<ShowsModel>> {
//        return getShowsModelWithGenres(remote.getTodayAiring())
//    }
//
//    override fun getPopularShows(): Observable<List<ShowsModel>> {
//        return getShowsModelWithGenres(remote.getPopularShows())
//    }
//
//    override fun getShowsByGenre(genreId: Int): Observable<List<ShowsModel>> {
//        return getShowsModelWithGenres(remote.getShowsByGenre(genreId))
//    }
//
//    override fun getShowsDetail(showsId: Int): Single<DetailShowsModel> {
//        return remote.getShowsDetail(showsId)
//            .map { response -> response.toModel() }
//    }
//
//    private fun getShowsModelWithGenres(observable: Observable<ShowsDtoResponse>): Observable<List<ShowsModel>> {
//        return Observable.zip(
//            observable,
//            remote.getShowsGenres(),
//            { dtoResponse, genresDtoResponse ->
//                dtoResponse.results?.map { showsDto ->
//                    showsDto.toModel().apply {
//                        this.genres = genresDtoResponse.genres?.filter { genresDto ->
//                            showsDto.genreIds!!.contains(genresDto.id)
//                        }?.take(3)?.toModels() as List<GenresModel>
//                    }
//                }
//            }
//        )
//    }
//}