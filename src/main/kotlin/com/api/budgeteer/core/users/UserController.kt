package com.api.budgeteer.core.users

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/users")
class UserController(private val userHandler: UserHandler)  {


    @GetMapping("")
    fun getUsers(): List<User> {
        return userHandler.getUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userHandler.getUserById(id)
    }

    @PostMapping("")
    fun createUser(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<User> {
        val newUser = userHandler.createUser(userDTO)
        return ResponseEntity(newUser,HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userHandler.deleteUser(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long,@Valid @RequestBody userDTO: UserDTO): ResponseEntity<User> {
        val updatedUser = userHandler.updateUser(id, userDTO)
        return ResponseEntity(updatedUser,HttpStatus.OK)
    }




}