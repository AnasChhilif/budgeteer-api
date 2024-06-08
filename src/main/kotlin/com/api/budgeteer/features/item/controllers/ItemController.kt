package com.api.budgeteer.features.item.controllers

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import com.api.budgeteer.features.item.toDTO
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemController (private val itemHandler: ItemHandler){
    @GetMapping()
    fun getItems() : List<ItemDTO> {
     return itemHandler.getItems().stream().map { toDTO(it) }.toList()
    }

    @PostMapping()
    fun createItem(@RequestBody @Valid itemDTO: ItemDTO): ItemDTO {
        val (_, userId, name, price, quantity) = itemDTO

        return toDTO(this.itemHandler.createItem(name, price, quantity, userId))
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