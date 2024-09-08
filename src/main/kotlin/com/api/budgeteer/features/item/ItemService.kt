package com.api.budgeteer.features.item

import com.api.budgeteer.core.aspects.ControllerLogger
import com.api.budgeteer.features.item.exceptions.ItemNotFoundException
import com.api.budgeteer.features.monthlydata.MonthlyDataHandler
import com.api.budgeteer.features.users.UserHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemService(private val itemRepository: ItemRepository, private val userHandler: UserHandler, private val monthlyDataHandler: MonthlyDataHandler)  : ItemHandler{

    private val LOG: Logger = LoggerFactory.getLogger(ControllerLogger::class.java)

    override fun createItem(name: String, price: Double, quantity: Int, userId: Long): Item {
        LOG.info("HEREEEEEEEEEEEEEE")
        LOG.info("HEREEEEEEEEEEEEEE")
        LOG.info("HEREEEEEEEEEEEEEE")
        LOG.info("HEREEEEEEEEEEEEEE")
        System.out.println("Testing to see if log works")
        val user = this.userHandler.getUserById(userId)
        LOG.info("HEREEEEEEEEEEEEEE")
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

        LOG.info(monthlyData.toString())

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