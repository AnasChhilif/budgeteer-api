package com.api.budgeteer.features.users

interface UserHandler {

    fun getUsers(): List<User>
    fun getUserById(id: Long): User
    fun getUserByEmail(email: String): User
    fun createUser(firstName: String, lastName: String, email: String): User
    fun deleteUser(id: Long): Unit
    fun updateUser(id: Long, user: User): User?
}