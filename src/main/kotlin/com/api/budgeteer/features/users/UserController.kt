package com.api.budgeteer.features.users

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/users")
class UserController(private val userHandler: UserHandler)  {

    private val logger = LoggerFactory.getLogger(UserController::class.java)


    @GetMapping("")
    fun getUsers(): List<User>{
        return userHandler.getUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userHandler.getUserById(id)
    }

    @PostMapping("")
    fun createUser( @RequestBody userDTO: UserDTO): ResponseEntity<User> {
        val  user = convertDTOToUser(userDTO)
        val newUser = userHandler.createUser(user)
        return ResponseEntity(newUser,HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userHandler.deleteUser(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<User> {
        val user = convertDTOToUser(userDTO)
        val updatedUser = userHandler.updateUser(id, user)
        return ResponseEntity(updatedUser,HttpStatus.OK)
    }


    fun convertDTOToUser(userDTO: UserDTO): User {
        return User(userDTO.firstName,userDTO.lastName, userDTO.email)
    }




}