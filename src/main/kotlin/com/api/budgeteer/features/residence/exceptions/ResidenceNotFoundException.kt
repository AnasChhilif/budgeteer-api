package com.api.budgeteer.features.residence.exceptions

class ResidenceNotFoundException: Exception {
    constructor(id: Long): super("Residence with id $id not found")
}