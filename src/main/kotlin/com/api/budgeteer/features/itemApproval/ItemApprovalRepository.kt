package com.api.budgeteer.features.itemApproval

import org.springframework.data.jpa.repository.JpaRepository

interface ItemApprovalRepository : JpaRepository<ItemApproval, Long> {
    fun findByItemId(itemId: Long): List<ItemApproval>
    fun findByUserId(userId: Long): List<ItemApproval>
    fun findByItemIdAndUserId(itemId: Long, userId: Long): ItemApproval?

}