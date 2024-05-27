package com.api.budgeteer.users

import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository)  : UserHandler{


    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id).get()
    }

    override fun createUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        }
    }


}