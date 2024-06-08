package com.api.budgeteer.features.monthlydata.controllers

import com.api.budgeteer.features.monthlydata.MonthlyData
import com.api.budgeteer.features.monthlydata.MonthlyDataDTO
import com.api.budgeteer.features.monthlydata.MonthlyDataHandler
import com.api.budgeteer.features.monthlydata.toDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/monthly-data")
class MonthlyDataController(private val monthlyDataHandler: MonthlyDataHandler) {

    @GetMapping
    fun getMonthlyData(): List<MonthlyDataDTO> {
        return monthlyDataHandler.getAllMonthlyData().stream().map { toDTO(it) }.toList()
    }

    @GetMapping("/{id}")
    fun getMonthlyDataById(@PathVariable id: Long): MonthlyDataDTO {
        return toDTO(monthlyDataHandler.getMonthlyData(id))
    }

    @GetMapping("/user/{userId}")
    fun getMonthlyDataByUser(@PathVariable userId: Long): List<MonthlyDataDTO> {
        return monthlyDataHandler.getMonthlyDataByUser(userId).stream().map { toDTO(it) }.toList()
    }

    @GetMapping("/residence/{residenceId}")
    fun getMonthlyDataByResidence(@PathVariable residenceId: Long): List<MonthlyDataDTO> {
        return monthlyDataHandler.getMonthlyDataByResidence(residenceId).stream().map { toDTO(it) }.toList()
    }

    @GetMapping("/user/{userId}/current")
    fun getCurrentMonthlyDataByUser(@PathVariable userId: Long): MonthlyDataDTO {
        return toDTO(monthlyDataHandler.getCurrentMonthlyDataByUser(userId).get())
    }

}