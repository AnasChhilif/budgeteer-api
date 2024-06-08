package com.api.budgeteer.features.monthlydata.DTOs

import com.api.budgeteer.features.users.UserDTO

data class DebtDTO(val user: UserDTO, val debtor: UserDTO, val amount: Double)
