package com.api.budgeteer.features.users.exceptions

enum class UserExceptionCode(val  code: Int) {
    USER_NOT_CREATED(3000),
    USER_NOT_FOUND(3001),
    USER_NOT_DELETED(3002),
    USER_NOT_UPDATED(3003),
    USER_EMAIL_NOT_FOUND(3004)

}