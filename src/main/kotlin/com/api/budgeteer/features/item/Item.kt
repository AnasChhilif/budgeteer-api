package com.api.budgeteer.features.item

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.users.User
import jakarta.persistence.*

@Entity
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val price: Double,
    val quantity: Int,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
) : TraceableEntity(){
    constructor(name: String, price: Double, quantity: Int, user: User) : this(0, name, price, quantity, user)
    constructor() : this(0, "itemName", 0.0, 0, User())

    fun toDTO() = ItemDTO(id, user.id, name, price, quantity)

    override fun toString(): String {
        return "Item(id=$id, name='$name', price='$price', quantity='$quantity')"
    }
}