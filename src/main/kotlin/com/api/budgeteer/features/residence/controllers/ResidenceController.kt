package com.api.budgeteer.features.residence.controllers

import com.api.budgeteer.features.residence.ResidenceHandler
import com.api.budgeteer.features.residence.dtos.ResidenceDTO
import com.api.budgeteer.features.residence.dtos.createResidenceDTO
import com.api.budgeteer.features.residence.toEntity
import com.api.budgeteer.features.users.UserDTO
import com.api.budgeteer.features.users.toDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/residences")
class ResidenceController(private val residenceHandler: ResidenceHandler) {

    @GetMapping()
    fun getResidences(): List<ResidenceDTO> {
        return residenceHandler.getResidences().map { com.api.budgeteer.features.residence.toDTO(it) }
    }

    @PostMapping()
    fun createResidence(@RequestBody createResidenceDTO: createResidenceDTO): ResidenceDTO {
        return com.api.budgeteer.features.residence.toDTO(
            residenceHandler.createResidence(
                createResidenceDTO.name,
                createResidenceDTO.address,
                createResidenceDTO.user
            )
        )
    }

    @GetMapping("/{id}")
    fun getResidenceById(id: Long): ResidenceDTO {
        return com.api.budgeteer.features.residence.toDTO(residenceHandler.getResidenceById(id))
    }

    @GetMapping("/{id}/users")
    fun getUsersByResidenceId(id: Long): List<UserDTO> {
        return residenceHandler.getResidenceById(id).users.map { toDTO(it) }
    }

    @PostMapping("/{residenceId}/users/{userEmail}")
    fun addUserToResidence(@PathVariable userEmail: String, @PathVariable residenceId: Long): ResidenceDTO {
        return com.api.budgeteer.features.residence.toDTO(residenceHandler.addUserToResidence(userEmail, residenceId))
    }

    @DeleteMapping("/{id}/users")
    fun removeUserFromResidence(userEmail: String, residenceId: Long): ResidenceDTO {
        return com.api.budgeteer.features.residence.toDTO(residenceHandler.removeUserFromResidence(userEmail, residenceId))
    }

    @DeleteMapping("/{id}")
    fun deleteResidence(id: Long) {
        residenceHandler.deleteResidence(id)
    }

    @PostMapping("/{id}")
    fun updateResidence(id: Long, @RequestBody residenceDTO: ResidenceDTO): ResidenceDTO {
        return com.api.budgeteer.features.residence.toDTO(residenceHandler.updateResidence(id, toEntity(residenceDTO)))
    }

}