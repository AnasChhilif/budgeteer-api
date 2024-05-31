package com.api.budgeteer.features.users

import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository)  : UserHandler {


    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id).get()
    }

    override fun createUser(firstName: String, lastName: String, email: String): User {
        val user = User(0, firstName, lastName, email)
        val userCreated = userRepository.save(user)
        return userRepository.save(userCreated)
    }

    override fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        }
    }

    override fun updateUser(id: Long, user: User): User? {
        val optionalUser = userRepository.findById(id)
        if (optionalUser.isPresent) {
            val existingUser = optionalUser.get()
            existingUser.email = user.email
            existingUser.firstName = user.firstName
            existingUser.lastName = user.lastName
            existingUser.updatedAt = user.updatedAt
            return userRepository.save(existingUser)
        }
        return null
    }



}