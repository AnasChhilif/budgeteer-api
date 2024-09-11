package com.api.budgeteer.features.item

import com.api.budgeteer.core.aspects.ControllerLogger
import com.api.budgeteer.features.item.exceptions.ItemApprovalNotCreatedException
import com.api.budgeteer.features.item.exceptions.ItemApprovalNotFoundException
import com.api.budgeteer.features.item.exceptions.ItemNotCreatedException
import com.api.budgeteer.features.item.exceptions.ItemNotFoundException
import com.api.budgeteer.features.itemApproval.ItemApproval
import com.api.budgeteer.features.itemApproval.ItemApprovalRepository
import com.api.budgeteer.features.monthlydata.MonthlyDataHandler
import com.api.budgeteer.features.residence.ResidenceHandler
import com.api.budgeteer.features.users.UserHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemService(private val itemRepository: ItemRepository,
                  private val userHandler: UserHandler,
                  private val monthlyDataHandler: MonthlyDataHandler,
                  private val itemApprovalRepository: ItemApprovalRepository,
                  private val residenceHandler: ResidenceHandler)  : ItemHandler{

    private val LOG: Logger = LoggerFactory.getLogger(ControllerLogger::class.java)

    override fun createItem(name: String, price: Double, quantity: Int, userId: Long): Item {
        LOG.info("Starting item creation process")

        val user = this.userHandler.getUserById(userId)
        val itemToSave = Item(name, price, quantity, user)


        val residence = residenceHandler.getResidenceByUserId(user.id)
        val residenceUsers = residence.users

        val savedItem: Item

        try {
            savedItem = itemRepository.save(itemToSave)
        } catch (e: Exception) {
            throw ItemNotCreatedException()
        }
        residenceUsers.forEach { residenceUser ->
            val itemApproval = ItemApproval(item = savedItem, user = residenceUser, approved = false)
            try {
                itemApprovalRepository.save(itemApproval)
            } catch (e: Exception) {
                throw ItemApprovalNotCreatedException()
            }
        }

        LOG.info("Item created and approval process initiated for residence users")

        return savedItem
    }

    override fun approveItem(itemId: Long, userId: Long): Boolean {
        LOG.info("User $userId is attempting to approve item $itemId")


        val approval = itemApprovalRepository.findByItemIdAndUserId(itemId, userId)
            ?: throw ItemApprovalNotFoundException(itemId, userId)


        approval.approved = true
        itemApprovalRepository.save(approval)

        val item = itemRepository.findById(itemId).orElseThrow { Exception("Item not found") }
        val allApproved = item.checkApprovalStatus()


        if (allApproved) {
            item.isApproved = true
            itemRepository.save(item)

            val monthlyData = this.monthlyDataHandler.getCurrentMonthlyDataByUser(item.user.id).orElseGet {
                item.user.residence?.let {
                    this.monthlyDataHandler.createMonthlyData(
                        item.user.id,
                        it.id,
                        0.0
                    )
                }
            }

            this.monthlyDataHandler.updateMonthlyData(monthlyData.id, monthlyData.amountSpent + (item.price * item.quantity))
            LOG.info("Item fully approved and monthly data updated")
        }

        return allApproved
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