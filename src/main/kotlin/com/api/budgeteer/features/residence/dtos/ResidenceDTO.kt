package com.api.budgeteer.features.residence.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.UniqueElements

data class ResidenceDTO (
    val id: Long? = null,

    @field:NotBlank(message = "Residence name is required")
    @field:UniqueElements
    val name: String,

    @field:NotBlank(message = "Residence address is required")
    val address: String,

    @field:NotBlank(message = "Residence users are required")
    @field:Size(min = 1, message = "Residence must have at least two users")
    val users: List<Long>
)