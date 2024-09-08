package com.api.budgeteer.features.users.controllers

import com.api.budgeteer.features.users.UserDTO
import com.api.budgeteer.features.users.UserHandler
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/users")
class UserController(private val userHandler: UserHandler)  {


    @GetMapping("")
    fun getUsers(): List<UserDTO> {
        return userHandler.getUsers().stream().map { it.toDTO() }.toList()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDTO {
        return userHandler.getUserById(id).toDTO()
    }

    @PostMapping("")
    fun createUser( @RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        val (_, firstName, lastName, email) = userDTO
        val newUser = userHandler.createUser(firstName, lastName, email)

        return ResponseEntity(newUser.toDTO(),HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userHandler.deleteUser(id)

        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userDTO.toEntity()
        val updatedUser = userHandler.updateUser(id, user)

        return ResponseEntity(updatedUser?.toDTO() ,HttpStatus.OK)
    }
}