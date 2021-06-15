package com.homindolentrahar.misbar.data.sources.remote

import android.text.method.SingleLineTransformationMethod
import com.homindolentrahar.misbar.data.sources.remote.dtos.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

//    @Query("api_key") apiKey: String = BuildConfig.API_KEY

    //    ===== Search =====
//    @GET("search/multi")
//    fun searchItems(
//        @Query("page") page: Int, @Query("query") query: String
//    ): Observable<SearchDtoResponse>

    @GET("search/multi")
    fun searchItems(
        @Query("page") page: Int, @Query("query") query: String
    ): Single<SearchDtoResponse>

//    ===== Movies =====

//    @GET("movie/now_playing")
//    fun getMoviesNowPlaying(): Observable<MoviesDtoResponse>

    @GET("movie/now_playing")
    fun getMoviesNowPlaying(): Single<MoviesDtoResponse>

    //    @GET("movie/popular")
//    fun getMoviesPopular(
//        @Query("page") page: Int
//    ): Observable<MoviesDtoResponse>
//
    @GET("movie/popular")
    fun getMoviesPopular(
        @Query("page") page: Int
    ): Single<MoviesDtoResponse>

//    @GET("discover/movie")
//    fun getMoviesByGenre(
//        @Query("page") page: Int, @Query("with_genres") genreId: Int
//    ): Observable<MoviesDtoResponse>

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("page") page: Int, @Query("with_genres") genreId: Int
    ): Single<MoviesDtoResponse>

    @GET("movie/{id}")
    fun getMoviesDetail(@Path("id") id: Int): Single<DetailMoviesDto>

//    @GET("genre/movie/list")
//    fun getMoviesGenres(): Observable<GenresDtoResponse>

    @GET("genre/movie/list")
    fun getMoviesGenres(): Single<GenresDtoResponse>

    //    ===== TV Shows =====

    @GET("tv/airing_today")
    fun getShowsTodayAiring(): Single<ShowsDtoResponse>

    @GET("tv/popular")
    fun getShowsPopular(
        @Query("page") page: Int
    ): Single<ShowsDtoResponse>

    @GET("discover/tv")
    fun getShowsByGenre(
        @Query("page") page: Int, @Query("with_genres") genreId: Int
    ): Single<ShowsDtoResponse>

    @GET("tv/{id}")
    fun getShowsDetail(@Path("id") id: Int): Single<DetailShowsDto>

    @GET("genre/tv/list")
    fun getShowsGenres(): Single<GenresDtoResponse>
}