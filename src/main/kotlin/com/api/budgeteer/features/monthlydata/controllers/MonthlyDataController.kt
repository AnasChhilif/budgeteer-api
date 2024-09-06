package com.api.budgeteer.features.monthlydata.controllers

import com.api.budgeteer.features.monthlydata.DTOs.DebtDTO
import com.api.budgeteer.features.monthlydata.DTOs.MonthlyDataDTO
import com.api.budgeteer.features.monthlydata.MonthlyDataHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/monthly-data")
class MonthlyDataController(private val monthlyDataHandler: MonthlyDataHandler) {

    @GetMapping
    fun getMonthlyData(): List<MonthlyDataDTO> {
        return monthlyDataHandler.getAllMonthlyData().stream().map { it.toDTO() }.toList()
    }

    @GetMapping("/{id}")
    fun getMonthlyDataById(@PathVariable id: Long): MonthlyDataDTO {
        return monthlyDataHandler.getMonthlyData(id).toDTO()
    }

    @GetMapping("/user/{userId}")
    fun getMonthlyDataByUser(@PathVariable userId: Long): List<MonthlyDataDTO> {
        return monthlyDataHandler.getMonthlyDataByUser(userId).stream().map { it.toDTO() }.toList()
    }

    @GetMapping("/residence/{residenceId}")
    fun getMonthlyDataByResidence(@PathVariable residenceId: Long): List<MonthlyDataDTO> {
        return monthlyDataHandler.getMonthlyDataByResidence(residenceId).stream().map { it.toDTO() }.toList()
    }

    @GetMapping("/user/{userId}/current")
    fun getCurrentMonthlyDataByUser(@PathVariable userId: Long): MonthlyDataDTO {
        return monthlyDataHandler.getCurrentMonthlyDataByUser(userId).get().toDTO()
    }


    @GetMapping("/user/{userId}/debt")
    fun getCurrentUserDebt(@PathVariable userId: Long): List<DebtDTO> {
        return monthlyDataHandler.getCurrentUserDebt(userId)
    }

}