package com.api.budgeteer.features.item
import com.api.budgeteer.features.users.User

data class ItemDTO(var id: Long?, var userId: Long, var name: String, var price: Double, var quantity: Int, var isApproved: Boolean = false)


{

    fun toEntity() = Item(id ?: 0, name, price, quantity, User(), mutableListOf(), isApproved)

}