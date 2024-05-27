package com.api.budgeteer.users

import org.springframework.beans.factory.annotation.Autowired
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
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val newUser = userHandler.createUser(user)
        return ResponseEntity(newUser,HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        userHandler.deleteUser(id)
        return ResponseEntity(HttpStatus.OK)
    }




}