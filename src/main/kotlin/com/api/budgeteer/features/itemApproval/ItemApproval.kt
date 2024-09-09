package com.api.budgeteer.features.itemApproval

import com.api.budgeteer.features.item.Item
import com.api.budgeteer.features.users.User
import jakarta.persistence.*

@Entity
@Table(name = "item_approvals")
data class ItemApproval(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    var approved: Boolean = false
)
