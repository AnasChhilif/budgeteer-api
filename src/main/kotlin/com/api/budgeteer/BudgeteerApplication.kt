package com.api.budgeteer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BudgeteerApplication

fun main(args: Array<String>) {
	runApplication<BudgeteerApplication>(*args)
}
