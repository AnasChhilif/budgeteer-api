package com.api.budgeteer.features.residence.exceptions

enum class ResidenceCodeException(val code: Int) {
    RESIDENCE_NOT_CREATED(4000),
    RESIDENCE_NOT_FOUND(4001),
    RESIDENCE_NOT_DELETED(4002),
    RESIDENCE_NOT_UPDATED(4003)

}