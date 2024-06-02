package com.api.budgeteer.features.item

interface ItemHandler {
    fun createItem(item: Item): Item
    fun getItems(): List<Item>
    fun getItemById(id: Long): Item
    fun getItemsByUserId(userId: Long): List<Item>
    fun updateItem(item: Item): Item
    fun deleteItem(id: Long)
}