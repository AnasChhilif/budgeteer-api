package com.api.budgeteer.controllers.item

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import com.api.budgeteer.features.item.controllers.ItemController
import com.api.budgeteer.features.residence.Residence
import com.api.budgeteer.features.users.User
import com.api.budgeteer.features.users.UserRepository
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

@WebMvcTest(ItemController::class)
class ItemTests {

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var itemHandler: ItemHandler

    private lateinit var objectMapper: ObjectMapper

    private val user =  User(1L, "user", "1", "user@example.com")

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
        `when`(userRepository.save(user)).thenReturn(user)
    }

    @Test
    fun `should return list of items`() {
        val items = listOf(
            ItemDTO(1, 1, "Item 1", 10.0, 1),
            ItemDTO(2, 1, "Item 2", 12.0, 1),
        )
        `when`(itemHandler.getItems()).thenReturn(items.map { it.toEntity() })

        mockMvc.perform(get("/items"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Item 1"))
            .andExpect(jsonPath("$[1].name").value("Item 2"))

        verify(itemHandler, times(1)).getItems()
    }

    @Test
    fun `should return an item`() {
        val item = ItemDTO(1, 1, "Item 1", 10.0, 1)
        `when`(itemHandler.getItemById(1)).thenReturn(item.toEntity())

        mockMvc.perform(get("/items/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Item 1"))

        verify(itemHandler, times(1)).getItemById(1)
    }

    @Test
    fun `should create an item`() {
        val item = ItemDTO(null, 1, "Item 1", 10.0, 1)
        `when`(itemHandler.createItem("Item 1", 10.0, 1, 1)).thenReturn(item.toEntity())

        mockMvc.perform(post("/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(item)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Item 1"))
            .andExpect(jsonPath("$.isApproved").value(false))

        verify(itemHandler, times(1)).createItem("Item 1", 10.0, 1, 1)
    }

    @Test
    fun `should return items of user`() {


        val items = listOf(
            ItemDTO(1L, user.id, "Item 1", 10.0, 1),  // Item for the user
            ItemDTO(2L, user.id, "Item 2", 12.0, 1),  // Another item for the user
            ItemDTO(3L, 2L, "Item 3", 15.0, 1)       // An item for a different user (userId 2)
        )

        `when`(itemHandler.getItemsByUserId(user.id)).thenReturn(items.filter { it.userId == user.id }.map { it.toEntity() })

        mockMvc.perform(get("/items/user/${user.id}"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2)) // 2 items for this user
            .andExpect(jsonPath("$[0].name").value("Item 1"))
            .andExpect(jsonPath("$[1].name").value("Item 2"))
            .andExpect(jsonPath("$[0].isApproved").value(false))
            .andExpect(jsonPath("$[1].isApproved").value(false))

        verify(itemHandler, times(1)).getItemsByUserId(user.id)
    }

    @Test
    fun `should approve an item`() {
        `when`(itemHandler.approveItem(1, 1)).thenReturn(true)

        mockMvc.perform(post("/items/1/approve?userId=1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("true"))

        verify(itemHandler, times(1)).approveItem(1, 1)

    }

}