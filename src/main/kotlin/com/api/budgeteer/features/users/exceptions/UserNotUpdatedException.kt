package com.api.budgeteer.features.users.exceptions

class UserNotUpdatedException : Exception {
    constructor(id: Long) : super("User with id $id not updated")
}