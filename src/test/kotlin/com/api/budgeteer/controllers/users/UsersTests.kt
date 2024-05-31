package com.api.budgeteer.features.users

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userHandler: UserHandler

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
    }

    @Test
    fun `getUsers should return list of users`() {
        val users = listOf(User("John", "Doe", "john.doe@example.com"))
        `when`(userHandler.getUsers()).thenReturn(users)

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[0].lastName").value("Doe"))
            .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
    }

    @Test
    fun `getUserById should return a user`() {
        val user = User("John", "Doe", "john.doe@example.com")
        `when`(userHandler.getUserById(1L)).thenReturn(user)

        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.email").value("john.doe@example.com"))
    }

    @Test
    fun `createUser should return created user`() {
        val userDTO = UserDTO(1, "Jane", "Doe", "jane.doe@example.com")
        val user = User("Jane", "Doe", "jane.doe@example.com")
        val createdUser = user.copy()
        `when`(userHandler.createUser(user.firstName,user.lastName,user.email)).thenReturn(createdUser)

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.firstName").value("Jane"))
            .andExpect(jsonPath("$.lastName").value("Doe"))
            .andExpect(jsonPath("$.email").value("jane.doe@example.com"))
    }

    @Test
    fun `deleteUser should return status OK`() {
        doNothing().`when`(userHandler).deleteUser(1L)

        mockMvc.perform(delete("/users/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun `updateUser should return updated user`() {
        val userDTO = UserDTO(1, "Jane", "Doe", "jane.doe@example.com")
        val user = User("Jane", "Doe", "jane.doe@example.com")
        val updatedUser = user.copy()
        `when`(userHandler.updateUser(1L, user)).thenReturn(updatedUser)

        mockMvc.perform(
            put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO))
        )
            .andExpect(status().isOk)
    }
}
