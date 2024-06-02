package com.api.budgeteer.features.item

import com.api.budgeteer.features.item.exceptions.ItemNotFoundException
import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository)  : ItemHandler{

    override fun createItem(item: Item): Item {
        return itemRepository.save(item)
    }

    override fun getItems(): List<Item> {
        return itemRepository.findAll()
    }

    override fun getItemById(id: Long): Item {
        return itemRepository.findById(id).orElseThrow{ ItemNotFoundException(id) }
    }

    override fun getItemsByUserId(userId: Long): List<Item> {
        return itemRepository.findByUserId(userId).orElseThrow{ ItemNotFoundException() }
    }

    override fun updateItem(item: Item): Item {
            return itemRepository.save(item)
        }

    override fun deleteItem(id: Long) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id)
        }
    }
}