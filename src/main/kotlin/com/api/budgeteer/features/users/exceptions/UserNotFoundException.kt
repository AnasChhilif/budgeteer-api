package com.api.budgeteer.features.users.exceptions

class UserNotFoundException : Exception{
    constructor(id: Long) : super("User with id $id not found")
}