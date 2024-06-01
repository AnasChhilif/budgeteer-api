package com.api.budgeteer.controllers.residences

import com.api.budgeteer.features.residence.*
import com.api.budgeteer.features.residence.dtos.ResidenceDTO
import com.api.budgeteer.features.users.User
import com.api.budgeteer.features.users.UserHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(ResidenceController::class)
class ResidenceTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var residenceHandler: ResidenceHandler

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
    }

    @Test
    fun `should return list of residences`() {
        val residences = listOf(
            ResidenceDTO(1, "Residence 1", "Address 1", listOf()),
            ResidenceDTO(2, "Residence 2", "Address 2", listOf())
        )
        `when`(residenceHandler.getResidences()).thenReturn(residences.map { toEntity(it) })

        mockMvc.perform(get("/residences"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Residence 1"))
            .andExpect(jsonPath("$[1].name").value("Residence 2"))

        verify(residenceHandler, times(1)).getResidences()
    }

    @Test
    fun `should return a residence`() {
        val residence = ResidenceDTO(1, "Residence 1", "Address 1", listOf())
        `when`(residenceHandler.getResidenceById(1)).thenReturn(toEntity(residence))

        mockMvc.perform(get("/residences/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Residence 1"))

        verify(residenceHandler, times(1)).getResidenceById(1)
    }

    @Test
    fun `should create a residence`() {
        val user = User(1, "John", "Doe", "user1@example.com")
        val residence = Residence(1, "Residence 1", "Address 1", listOf(user))
        val residenceDTO = ResidenceDTO(0, "Residence 1", "Address 1", listOf(1))

        `when`(residenceHandler.createResidence(anyString(), anyString(), anyLong())).thenReturn(residence)

        mockMvc.perform(
            post("/residences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residenceDTO))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Residence 1"))
            .andExpect(jsonPath("$.address").value("Address 1"))

        verify(residenceHandler, times(1)).createResidence(anyString(), anyString(), anyLong())
    }

    @Test
    fun `should delete a residence`() {
        doNothing().`when`(residenceHandler).deleteResidence(1)

        mockMvc.perform(delete("/residences/1"))
            .andExpect(status().isOk)

        verify(residenceHandler, times(1)).deleteResidence(1)
    }


    @Test
    fun `should update a residence`() {
        val updatedResidence = Residence(1, "Updated Residence", "Updated Address", listOf())
        val residenceDTO = ResidenceDTO(1, "Updated Residence", "Updated Address", listOf())

        `when`(residenceHandler.updateResidence(1, toEntity(residenceDTO))).thenReturn(updatedResidence)

        mockMvc.perform(
            put("/residences/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residenceDTO))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Updated Residence"))
            .andExpect(jsonPath("$.address").value("Updated Address"))
    }





    @Test
    fun `should return list of users by residence id`() {
        val residence = Residence(1, "Residence 1", "Address 1", listOf(User(1, "John", "Doe", "john.doe@gmail.com")))
        `when`(residenceHandler.getResidenceById(1)).thenReturn(residence)

        mockMvc.perform(get("/residences/1/users"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[0].lastName").value("Doe"))

        verify(residenceHandler, times(1)).getResidenceById(1)

    }

    @Test
    fun `should add user to residence`() {
        val user = User(1, "John", "Doe", "user1@example.com")
        val residence = Residence(1, "Residence 1", "Address 1", listOf(user))
        val updatedResidence = Residence(1, "Residence 1", "Address 1", listOf(user, User(2, "Jane", "Doe", "user2@example.com")))
        val residenceDTO = ResidenceDTO(1, "Residence 1", "Address 1", listOf(1, 2))


        `when`(residenceHandler.addUserToResidence(anyLong(), anyLong())).thenReturn(updatedResidence)

        mockMvc.perform(
            post("/residences/1/users")
                .param("userId", "2")
                .param("residenceId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residenceDTO))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Residence 1"))
            .andExpect(jsonPath("$.address").value("Address 1"))
            .andExpect(jsonPath("$.users").isArray)
            .andExpect(jsonPath("$.users.length()").value(2))

        verify(residenceHandler, times(1)).addUserToResidence(2L, 1L)
    }

    @Test
    fun `should remove user from residence`() {
        val user = User(1, "John", "Doe", "john.doe@gmail.com")
        val residence = Residence(1, "Residence 1", "Address 1", listOf(user))
        val updatedResidence = Residence(1, "Residence 1", "Address 1", listOf())
        val residenceDTO = ResidenceDTO(1, "Residence 1", "Address 1", listOf())


        `when`(residenceHandler.removeUserFromResidence(anyLong(), anyLong())).thenReturn(updatedResidence)

        mockMvc.perform(
            delete("/residences/1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(residenceDTO))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Residence 1"))
            .andExpect(jsonPath("$.address").value("Address 1"))
            .andExpect(jsonPath("$.users").isArray)
            .andExpect(jsonPath("$.users.length()").value(0))

        verify(residenceHandler, times(1)).removeUserFromResidence(1L, 1L)

    }





}