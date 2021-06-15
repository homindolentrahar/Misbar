package com.homindolentrahar.misbar.data.sources.remote

import com.homindolentrahar.misbar.data.sources.remote.dtos.DetailShowsDto
import com.homindolentrahar.misbar.data.sources.remote.dtos.GenresDtoResponse
import com.homindolentrahar.misbar.data.sources.remote.dtos.ShowsDtoResponse
import io.reactivex.rxjava3.core.Single

interface ShowsRemoteDataSource {
    fun getTodayAiring(): Single<ShowsDtoResponse>

    fun getPopularShows(page: Int): Single<ShowsDtoResponse>

    fun getShowsByGenre(page: Int, genreId: Int): Single<ShowsDtoResponse>

    fun getShowsDetail(showsId: Int): Single<DetailShowsDto>

    fun getShowsGenres(): Single<GenresDtoResponse>
}

class
RetrofitShowsRemoteDataSource(private val tmdbApi: TMDBApi) : ShowsRemoteDataSource {
    override fun getTodayAiring(): Single<ShowsDtoResponse> {
        return tmdbApi.getShowsTodayAiring()
    }

    override fun getPopularShows(page: Int): Single<ShowsDtoResponse> {
        return tmdbApi.getShowsPopular(page)
    }

    override fun getShowsByGenre(page: Int, genreId: Int): Single<ShowsDtoResponse> {
        return tmdbApi.getShowsByGenre(page, genreId)
    }

    override fun getShowsDetail(showsId: Int): Single<DetailShowsDto> {
        return tmdbApi.getShowsDetail(id = showsId)
    }

    override fun getShowsGenres(): Single<GenresDtoResponse> {
        return tmdbApi.getShowsGenres()
    }
}