package com.api.budgeteer.features.item

data class ItemDTO(var id: Long?, var userId: Long, var name: String, var price: Double, var quantity: Int, var isApproved: Boolean = false)
