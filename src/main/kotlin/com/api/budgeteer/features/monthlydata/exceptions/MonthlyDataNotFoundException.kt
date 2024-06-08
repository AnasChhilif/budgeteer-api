package com.api.budgeteer.features.monthlydata.exceptions

class MonthlyDataNotFoundException: Exception {
    constructor(id: Long): super("Monthly data with id $id not found")
    constructor(): super("Monthly data not found")
}