package com.api.budgeteer.features.monthlydata

import com.api.budgeteer.features.monthlydata.DTOs.DebtDTO
import com.api.budgeteer.features.monthlydata.DTOs.DebtListDTO
import java.util.*

interface MonthlyDataHandler {
    fun createMonthlyData(userId: Long, residenceId: Long, initialSpending: Double): MonthlyData
    fun getMonthlyData(id: Long): MonthlyData
    fun getAllMonthlyData(): List<MonthlyData>
    fun getMonthlyDataByUser(userId: Long): List<MonthlyData>
    fun getMonthlyDataByResidence(residenceId: Long): List<MonthlyData>
    fun getCurrentMonthlyDataByUser(userId: Long): Optional<MonthlyData>
    fun getCurrentUserDebt(userId: Long): List<DebtDTO>
    fun updateMonthlyData(id: Long, newAmount: Double): MonthlyData
    fun deleteMonthlyData(id: Long)
}