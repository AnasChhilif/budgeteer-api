package com.api.budgeteer.features.monthlydata

import com.api.budgeteer.features.monthlydata.DTOs.DebtDTO
import com.api.budgeteer.features.monthlydata.exceptions.MonthlyDataNotFoundException
import com.api.budgeteer.features.residence.Residence
import com.api.budgeteer.features.residence.ResidenceHandler
import com.api.budgeteer.features.residence.exceptions.ResidenceNotFoundException
import com.api.budgeteer.features.users.User
import com.api.budgeteer.features.users.UserHandler
import com.api.budgeteer.features.users.exceptions.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.*

@Service
@Transactional
class MonthlyDataService(private val monthlyDataRepository: MonthlyDateRepository, private val userHandler: UserHandler, private val residenceHandler: ResidenceHandler): MonthlyDataHandler{
    override fun createMonthlyData(userId: Long, residenceId: Long, initialSpending: Double): MonthlyData {
        val user: User
        val residence: Residence
        try {
            user = userHandler.getUserById(userId)
            residence = residenceHandler.getResidenceById(residenceId)
        } catch (e: UserNotFoundException) {
            throw UserNotFoundException(userId)
        } catch (e: ResidenceNotFoundException) {
            throw ResidenceNotFoundException(residenceId)
        }

        val now = LocalDate.now()
        val startDate = now.with(TemporalAdjusters.firstDayOfMonth())
        val endDate = now.with(TemporalAdjusters.lastDayOfMonth())

        val monthlyData = MonthlyData(startDate, endDate, user, residence, initialSpending)

        return monthlyDataRepository.save(monthlyData)
    }


    override fun getMonthlyData(id: Long): MonthlyData {
        return monthlyDataRepository.findById(id).orElseThrow{ MonthlyDataNotFoundException(id) }
    }

    override fun getAllMonthlyData(): List<MonthlyData> {
        return this.monthlyDataRepository.findAll()
    }

    override fun getMonthlyDataByUser(userId: Long): List<MonthlyData> {
        return this.monthlyDataRepository.findByUserId(userId)
    }

    override fun getMonthlyDataByResidence(residenceId: Long): List<MonthlyData> {
        return this.monthlyDataRepository.findByResidenceId(residenceId)
    }

    override fun getCurrentMonthlyDataByUser(userId: Long) : Optional<MonthlyData> {
        return this.monthlyDataRepository.findCurrentMonthlyDataByUserId(userId)
    }

    override fun getCurrentUserDebt(userId: Long): List<DebtDTO> {
        val residence = this.residenceHandler.getResidenceByUserId(userId)
        val debtOwner = this.userHandler.getUserById(userId)
        val monthlyData = this.getCurrentMonthlyDataByUser(userId).orElseThrow{ MonthlyDataNotFoundException(userId) }
        val debtList = mutableListOf<DebtDTO>()
        val totalUsers = residence.users.size

        for (user in residence.users) {
            if (user.id != userId) {
                val userMonthlyData = this.getCurrentMonthlyDataByUser(user.id).orElseThrow{ MonthlyDataNotFoundException(user.id) }
                val debt = (userMonthlyData.amountSpent / totalUsers) - (monthlyData.amountSpent / totalUsers)
                val debtDTO = DebtDTO(com.api.budgeteer.features.users.toDTO(debtOwner), com.api.budgeteer.features.users.toDTO(user), debt)
                debtList.add(debtDTO)
            }
        }

        return debtList
    }

    override fun updateMonthlyData(id: Long, newAmount: Double): MonthlyData {
        val monthlyData = monthlyDataRepository.findById(id).orElseThrow{ MonthlyDataNotFoundException(id) }
        monthlyData.amountSpent = newAmount
        monthlyData.updatedAt = LocalDateTime.now()

        return monthlyDataRepository.save(monthlyData)
    }

    override fun deleteMonthlyData(id: Long) {
        if (monthlyDataRepository.existsById(id)) {
            monthlyDataRepository.deleteById(id)
        }
    }
}