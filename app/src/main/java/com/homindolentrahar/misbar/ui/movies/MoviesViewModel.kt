package com.homindolentrahar.misbar.ui.movies

import androidx.lifecycle.ViewModel
import com.homindolentrahar.misbar.data.movies.LocalMoviesRepository
import com.homindolentrahar.misbar.domain.core.ItemModel

class MoviesViewModel : ViewModel() {
    var repository = LocalMoviesRepository()

    fun getMovies(): List<ItemModel> =
        repository.getItemsFromLocalSource().map { entity -> entity.toModel() }

    fun getDetailMovie(movieId: Int): ItemModel =
        repository.getSpecificItemFromLocalSource(movieId)?.toModel() ?: ItemModel.empty()
}