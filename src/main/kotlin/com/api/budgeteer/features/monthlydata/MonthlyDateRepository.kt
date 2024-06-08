package com.api.budgeteer.features.monthlydata

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MonthlyDateRepository: JpaRepository<MonthlyData, Long>{
    fun findByUserId(userId: Long): List<MonthlyData>
    fun findByResidenceId(residenceId: Long): List<MonthlyData>

    @Query("SELECT md FROM MonthlyData md WHERE md.user.id = :userId AND MONTH(md.startDate) = MONTH(CURRENT_DATE()) AND YEAR(md.startDate) = YEAR(CURRENT_DATE())")
    fun findCurrentMonthlyDataByUserId(userId: Long): Optional<MonthlyData>
}