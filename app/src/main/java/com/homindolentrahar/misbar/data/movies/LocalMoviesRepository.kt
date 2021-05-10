package com.homindolentrahar.misbar.data.movies

import com.homindolentrahar.misbar.data.core.ItemLocalEntity
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.domain.core.LocalItemsRepository

class LocalMoviesRepository : LocalItemsRepository {
    override fun getItemsFromLocalSource(): List<ItemLocalEntity> = LocalItems.localMovies

    override fun getSpecificItemFromLocalSource(itemId: Int): ItemLocalEntity? =
        LocalItems.localMovies.firstOrNull { entity -> entity.id == itemId }
}