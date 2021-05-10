package com.homindolentrahar.misbar.data.shows

import com.homindolentrahar.misbar.data.core.ItemLocalEntity
import com.homindolentrahar.misbar.data.core.LocalItems
import com.homindolentrahar.misbar.domain.core.LocalItemsRepository

class LocalShowsRepository : LocalItemsRepository {
    override fun getItemsFromLocalSource(): List<ItemLocalEntity> = LocalItems.localShows

    override fun getSpecificItemFromLocalSource(itemId: Int): ItemLocalEntity? =
        LocalItems.localShows.firstOrNull { entity -> entity.id == itemId }
}