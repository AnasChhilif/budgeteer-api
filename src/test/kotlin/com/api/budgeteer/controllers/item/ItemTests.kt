package com.api.budgeteer.controllers.item

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import com.api.budgeteer.features.item.controllers.ItemController
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

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var itemHandler: ItemHandler

    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = ObjectMapper()
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
}