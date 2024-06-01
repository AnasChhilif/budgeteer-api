package com.api.budgeteer.features.residence

import com.api.budgeteer.features.residence.dtos.ResidenceDTO

fun toDTO(residence: Residence): ResidenceDTO {
    val (id, name, address, users) = residence
    println(residence)
    return ResidenceDTO(id, name, address, users.map { it.id })
}

fun toEntity(residenceDTO: ResidenceDTO): Residence {
    return Residence(residenceDTO.id ?: 0, residenceDTO.name, residenceDTO.address, emptyList())
}