package com.api.budgeteer.features.item

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ItemRepository : JpaRepository<Item, Long> {

    fun findByUserId(userId: Long): Optional<List<Item>>
}