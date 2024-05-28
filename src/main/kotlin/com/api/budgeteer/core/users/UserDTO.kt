package com.api.budgeteer.core.users

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class UserDTO(
    val id: Long? = null,

    @field:NotBlank(message = "First name is required")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    val lastName: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotNull(message = "createdAt cannot be null")
    val createdAt: String,

    @field:NotNull(message = "updatedAt cannot be null")
    val updatedAt: String,

    val deletedAt: String? = null
)
