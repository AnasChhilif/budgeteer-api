package com.api.budgeteer.features.users

import com.api.budgeteer.features.users.exceptions.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserService(private val userRepository: UserRepository)  : UserHandler {
    override fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow{UserNotFoundException(id)}
    }

    override fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserEmailNotFoundException(email)
    }

    override fun createUser(firstName: String, lastName: String, email: String): User {
        val user = User(0, firstName, lastName, email)
        val savedUser = userRepository.save(user)
        if (savedUser.id == 0L) {
            throw UserNotCreatedException()
        }
        return savedUser
    }

    override fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            if (userRepository.existsById(id)) {
                throw UserNotDeletedException()
            }
        }else{
            throw UserNotFoundException(id)
        }
    }

    override fun updateUser(id: Long, user: User): User? {
        val optionalUser = userRepository.findById(id)
        if (optionalUser.isPresent) {
            val userToUpdate = optionalUser.get()
            userToUpdate.firstName = user.firstName
            userToUpdate.lastName = user.lastName
            userToUpdate.email = user.email
            val updatedUser = userRepository.save(userToUpdate)
            if (updatedUser == null) {
                throw UserNotUpdatedException(id)
            }
            return updatedUser
        } else {
            throw UserNotFoundException(id)
        }
    }
}