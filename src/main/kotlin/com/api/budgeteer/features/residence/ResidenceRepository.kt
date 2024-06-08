package com.api.budgeteer.features.residence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ResidenceRepository : JpaRepository<Residence, Long>{
    @Query("SELECT r FROM Residence r JOIN r.users u WHERE u.id = :userId")
    fun findByUserId(userId: Long): Optional<Residence>
}