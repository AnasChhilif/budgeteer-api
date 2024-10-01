package com.api.budgeteer.features.users.controllers

import com.api.budgeteer.features.users.UserDTO
import com.api.budgeteer.features.users.UserHandler
import com.api.budgeteer.features.users.exceptions.*
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
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        return try {
            val user = userHandler.getUserById(id)
            ResponseEntity(user.toDTO(), HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_FOUND.code))
        }
    }

    @PostMapping("")
    fun createUser( @RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        val (_, firstName, lastName, email) = userDTO
        return try {
            ResponseEntity(userHandler.createUser(firstName, lastName, email).toDTO(), HttpStatus.CREATED)
        } catch (e: UserNotCreatedException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_CREATED.code))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            userHandler.deleteUser(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: UserNotDeletedException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_DELETED.code))
        } catch (e: UserNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_FOUND.code))
        }
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userDTO.toEntity()
        return try {
            val updatedUser = userHandler.updateUser(id, user)
            ResponseEntity(updatedUser!!.toDTO(), HttpStatus.OK)
        } catch (e: UserNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_FOUND.code))
        } catch (e : UserNotUpdatedException){
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_UPDATED.code))
        }
    }
}