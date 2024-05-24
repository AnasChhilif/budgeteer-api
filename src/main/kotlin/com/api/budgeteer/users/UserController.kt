package com.api.budgeteer.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/users")
class UserController (@Autowired private val userRepository: UserRepository) {

    @GetMapping("")
    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userRepository.findById(id).get()
    }

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val newUser = userRepository.save(user)
        return ResponseEntity(newUser,HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id)
            return ResponseEntity(Unit,HttpStatus.OK)
        }
        return ResponseEntity(Unit,HttpStatus.NOT_FOUND)
    }




}