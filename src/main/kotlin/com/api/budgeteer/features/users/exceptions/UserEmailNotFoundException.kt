package com.api.budgeteer.features.users.exceptions

class UserEmailNotFoundException : Exception {
    constructor(email: String) : super("User with email $email not found")
}