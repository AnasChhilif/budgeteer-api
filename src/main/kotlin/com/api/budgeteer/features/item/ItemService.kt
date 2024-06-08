package com.api.budgeteer.features.item

import com.api.budgeteer.features.item.exceptions.ItemNotFoundException
import com.api.budgeteer.features.monthlydata.MonthlyDataHandler
import com.api.budgeteer.features.users.UserHandler
import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository, private val userHandler: UserHandler, private val monthlyDataHandler: MonthlyDataHandler)  : ItemHandler{

    override fun createItem(name: String, price: Double, quantity: Int, userId: Long): Item {
        val user = this.userHandler.getUserById(userId)
        val itemToSave = Item(name, price, quantity, user)

        val monthlyData = this.monthlyDataHandler.getCurrentMonthlyDataByUser(user.id).orElseGet {
            user.residence?.let {
                this.monthlyDataHandler.createMonthlyData(
                    user.id,
                    it.id,
                    0.0
                )
            }
        }


        this.monthlyDataHandler.updateMonthlyData(monthlyData.id, monthlyData.amountSpent + (price * quantity))

        return itemRepository.save(itemToSave)
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