package com.api.budgeteer.features.monthlydata.exceptions

enum class MonthlyDataExceptionCode(val code: Int) {
    MONTHLY_DATA_NOT_CREATED(5000),
    MONTHLY_DATA_NOT_FOUND(5001),
    MONTHLY_DATA_NOT_DELETED(5002),
    MONTHLY_DATA_NOT_UPDATED(5003)
}