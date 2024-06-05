package com.api.budgeteer.features.residence.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.UniqueElements

data class createResidenceDTO (
    @field:NotBlank(message = "Residence name is required")
    @field:UniqueElements
    val name: String,

    @field:NotBlank(message = "Residence address is required")
    val address: String,

    @field:NotBlank(message = "Residence users are required")
    @field:Size(min = 1, message = "Residence must have at least one user")
    val user: Long
)