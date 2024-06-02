package com.api.budgeteer.features.item.controllers

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import com.api.budgeteer.features.item.toDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemController (private val itemHandler: ItemHandler){
    @GetMapping()
    fun getItems() : List<ItemDTO> {
     return itemHandler.getItems().stream().map { toDTO(it) }.toList()
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ItemDTO {
        return toDTO(itemHandler.getItemById(id))
    }

    @GetMapping("/user/{userId}")
    fun getItemsByUserId(@PathVariable userId: Long): List<ItemDTO> {
        return itemHandler.getItemsByUserId(userId).stream().map { toDTO(it) }.toList()
    }

}