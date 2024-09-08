package com.api.budgeteer.features.item

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.itemApproval.ItemApproval
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
    val user: User,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var approvals: List<ItemApproval> = mutableListOf(),

    var isApproved: Boolean = false // Fully approved by all users


) : TraceableEntity(){
    constructor(name: String, price: Double, quantity: Int, user: User) : this(0, name, price, quantity, user, mutableListOf(), false)
    constructor() : this(0, "itemName", 0.0, 0, User(), mutableListOf(), false)

    fun toDTO() = ItemDTO(id, user.id, name, price, quantity, isApproved)

    fun checkApprovalStatus(): Boolean {
        this.isApproved = approvals.all { it.approved }
        return this.isApproved
    }

    override fun toString(): String {
        return "Item(id=$id, name='$name', price='$price', quantity='$quantity', isApproved='$isApproved')"
    }
}