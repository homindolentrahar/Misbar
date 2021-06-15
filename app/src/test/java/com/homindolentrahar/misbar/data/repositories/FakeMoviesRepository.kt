//package com.homindolentrahar.misbar.data.repositories
//
//import com.homindolentrahar.misbar.data.sources.remote.MoviesRemoteDataSource
//import com.homindolentrahar.misbar.data.sources.remote.dtos.MoviesDtoResponse
//import com.homindolentrahar.misbar.data.sources.remote.dtos.toModel
//import com.homindolentrahar.misbar.data.sources.remote.dtos.toModels
//import com.homindolentrahar.misbar.domain.models.DetailMoviesModel
//import com.homindolentrahar.misbar.domain.models.MoviesModel
//import com.homindolentrahar.misbar.domain.models.GenresModel
//import com.homindolentrahar.misbar.domain.repositories.MoviesRepository
//import io.reactivex.rxjava3.core.Observable
//import io.reactivex.rxjava3.core.Single
//
//class FakeMoviesRepository(private val remote: MoviesRemoteDataSource) : MoviesRepository {
//
//    override fun getNowPlaying(): Observable<List<MoviesModel>> {
//        return getMoviesModelWithGenres(remote.getNowPlaying())
//    }
//
//    override fun getPopularMovies(): Observable<List<MoviesModel>> {
//        return getMoviesModelWithGenres(remote.getPopularMovies())
//    }
//
//    override fun getMoviesByGenre(genreId: Int): Observable<List<MoviesModel>> {
//        return getMoviesModelWithGenres(remote.getMoviesByGenre(genreId))
//    }
//
//    override fun getMoviesDetail(moviesId: Int): Single<DetailMoviesModel> {
//        return remote.getMoviesDetail(moviesId)
//            .map { response -> response.toModel() }
//    }
//
//    private fun getMoviesModelWithGenres(observable: Observable<MoviesDtoResponse>): Observable<List<MoviesModel>> {
//        return Observable.zip(
//            observable,
//            remote.getMoviesGenres(),
//            { dtoResponse, genresDtoResponse ->
//                dtoResponse.results?.map { moviesDto ->
//                    moviesDto.toModel().apply {
//                        this.genres = genresDtoResponse.genres?.filter { genresDto ->
//                            moviesDto.genreIds!!.contains(genresDto.id)
//                        }?.take(3)?.toModels() as List<GenresModel>
//                    }
//                }
//            }
//        )
//    }
//}