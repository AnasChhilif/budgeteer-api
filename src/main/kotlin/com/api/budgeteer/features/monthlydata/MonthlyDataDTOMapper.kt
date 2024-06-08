package com.api.budgeteer.features.monthlydata

fun toDTO(monthlyData: MonthlyData): MonthlyDataDTO {
    return MonthlyDataDTO(
            id = monthlyData.id,
            startDate = monthlyData.startDate.toString(),
            endDate = monthlyData.endDate.toString(),
            userId = monthlyData.user.id,
            residenceId = monthlyData.residence.id,
            amountSpent = monthlyData.amountSpent
    )
}