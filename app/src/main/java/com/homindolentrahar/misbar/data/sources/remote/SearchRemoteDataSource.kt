package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.data.sources.remote.dtos.GenresDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.SearchDtoResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SearchRemoteDataSource {
    fun searchItems(page: Int, query: String): Single<SearchDtoResponse>

    fun getMoviesGenres(): Single<GenresDtoResponse>

    fun getShowsGenres(): Single<GenresDtoResponse>
}

class RetrofitSearchRemoteDataSource(private val tmdbApi: TMDBApi) : SearchRemoteDataSource {

    override fun searchItems(page: Int, query: String): Single<SearchDtoResponse> {
        return tmdbApi.searchItems(page, query)
    }

    override fun getMoviesGenres(): Single<GenresDtoResponse> {
        return tmdbApi.getMoviesGenres()
    }

    override fun getShowsGenres(): Single<GenresDtoResponse> {
        return tmdbApi.getShowsGenres()
    }
}