package com.homindolentrahar.misbar.ui.shows

import androidx.lifecycle.ViewModel
import com.homindolentrahar.misbar.data.shows.LocalShowsRepository
import com.homindolentrahar.misbar.domain.core.ItemModel

class ShowsViewModel : ViewModel() {
    var repository = LocalShowsRepository()

    fun getShows(): List<ItemModel> =
        repository.getItemsFromLocalSource().map { entity -> entity.toModel() }

    fun getDetailShow(showId: Int): ItemModel =
        repository.getSpecificItemFromLocalSource(showId)?.toModel() ?: ItemModel.empty()
}