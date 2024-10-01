package com.api.budgeteer.features.item.controllers

import com.api.budgeteer.features.item.ItemDTO
import com.api.budgeteer.features.item.ItemHandler
import com.api.budgeteer.features.item.exceptions.*
import com.api.budgeteer.features.itemApproval.exceptions.ItemApprovalExceptionCode
import com.api.budgeteer.features.itemApproval.exceptions.ItemApprovalNotCreatedException
import com.api.budgeteer.features.itemApproval.exceptions.ItemApprovalNotFoundException
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
class ItemController (private val itemHandler: ItemHandler){
    @GetMapping()
    fun getItems() : List<ItemDTO> {
        return itemHandler.getItems().map { it.toDTO() }.toList()
    }

    @PostMapping()
    fun createItem(@RequestBody @Valid itemDTO: ItemDTO): ResponseEntity<ItemDTO> {
        val (_, userId, name, price, quantity) = itemDTO
        return try {
            ResponseEntity<ItemDTO>(this.itemHandler.createItem(name, price, quantity, userId).toDTO(), HttpStatus.CREATED)
        } catch (e: ItemNotCreatedException) {
            ResponseEntity<ItemDTO>(HttpStatus.valueOf(ItemExceptionCode.ITEM_NOT_CREATED.code))
        } catch (e : ItemApprovalNotCreatedException){
            ResponseEntity<ItemDTO>(HttpStatus.valueOf(ItemApprovalExceptionCode.ITEM_APPROVAL_NOT_CREATED.code))
        }

    }

    @PostMapping("/{itemId}/approve")
    fun approveItem(@PathVariable itemId: Long, @RequestParam userId: Long): ResponseEntity<Boolean> {
        return try {
            ResponseEntity<Boolean>(itemHandler.approveItem(itemId, userId), HttpStatus.OK)
        } catch (e: ItemApprovalNotFoundException) {
            ResponseEntity<Boolean>(HttpStatus.valueOf(ItemApprovalExceptionCode.ITEM_APPROVAL_NOT_FOUND.code))
        }
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ResponseEntity<ItemDTO> {
        return try {
            ResponseEntity<ItemDTO>(itemHandler.getItemById(id).toDTO(), HttpStatus.OK)
        } catch (e: ItemNotFoundException) {
            ResponseEntity<ItemDTO>(HttpStatus.valueOf(ItemExceptionCode.ITEM_NOT_FOUND.code))
        }

    }

    @GetMapping("/user/{userId}")
    fun getItemsByUserId(@PathVariable userId: Long): ResponseEntity<List<ItemDTO>> {
        return try {
            ResponseEntity<List<ItemDTO>>(itemHandler.getItemsByUserId(userId).map { it.toDTO() }.toList(), HttpStatus.OK)
        } catch (e: ItemNotFoundException) {
            ResponseEntity<List<ItemDTO>>(HttpStatus.valueOf(ItemExceptionCode.ITEM_NOT_FOUND.code))
        }

    }

}