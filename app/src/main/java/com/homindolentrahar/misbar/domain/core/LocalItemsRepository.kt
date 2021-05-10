package com.homindolentrahar.misbar.domain.core

import com.homindolentrahar.misbar.data.core.ItemLocalEntity

interface LocalItemsRepository {
    fun getItemsFromLocalSource(): List<ItemLocalEntity>
    fun getSpecificItemFromLocalSource(itemId: Int): ItemLocalEntity?
}