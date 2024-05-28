package com.api.budgeteer.features.users

interface UserHandler {

    fun getUsers(): List<User>
    fun getUserById(id: Long): User
    fun createUser(user: User): User
    fun deleteUser(id: Long): Unit
    fun updateUser(id: Long, user: User): User?
}