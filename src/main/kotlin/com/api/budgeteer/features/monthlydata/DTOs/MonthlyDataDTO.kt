package com.api.budgeteer.features.monthlydata.DTOs

data class MonthlyDataDTO(
        val id: Long,
        val startDate: String,
        val endDate: String,
        val userId: Long,
        val residenceId: Long,
        val amountSpent: Double
)
