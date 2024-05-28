package com.api.budgeteer.core.users

import com.api.budgeteer.core.Traceable
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository)  : UserHandler {


    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id).get()
    }

    override fun createUser(user: User): User {
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
            existingUser.name = user.name
            existingUser.email = user.email
            return userRepository.save(existingUser)
        }
        return null
    }



}