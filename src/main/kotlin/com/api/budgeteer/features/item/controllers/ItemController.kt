package com.api.budgeteer.features.item.controllers

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemController (private val itemHandler: ItemHandler){
    @GetMapping()
    fun getItems() : List<ItemDTO> {
        return itemHandler.getItems().map { it.toDTO() }.toList()
    }

    @PostMapping()
    fun createItem(@RequestBody @Valid itemDTO: ItemDTO): ItemDTO {
        val (_, userId, name, price, quantity) = itemDTO

        System.out.println("-----------------TEST---------------------")

        return this.itemHandler.createItem(name, price, quantity, userId).toDTO()
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ItemDTO {
        return itemHandler.getItemById(id).toDTO()
    }

    @GetMapping("/user/{userId}")
    fun getItemsByUserId(@PathVariable userId: Long): List<ItemDTO> {
        return itemHandler.getItemsByUserId(userId).stream().map { it.toDTO() }.toList()
    }

}