package com.api.budgeteer.features.residence.controllers

import com.api.budgeteer.features.residence.ResidenceHandler
import com.api.budgeteer.features.residence.dtos.ResidenceDTO
import com.api.budgeteer.features.residence.dtos.createResidenceDTO
import com.api.budgeteer.features.users.UserDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/residences")
class ResidenceController(private val residenceHandler: ResidenceHandler) {

    @GetMapping()
    fun getResidences(): List<ResidenceDTO> {
        return residenceHandler.getResidences().map { it.toDTO() }
    }

    @PostMapping()
    fun createResidence(@RequestBody createResidenceDTO: createResidenceDTO): ResidenceDTO {
        return residenceHandler.createResidence(
                createResidenceDTO.name,
                createResidenceDTO.address,
                createResidenceDTO.user
            ).toDTO()
    }

    @GetMapping("/{id}")
    fun getResidenceById(@PathVariable id: Long): ResidenceDTO {
        return residenceHandler.getResidenceById(id).toDTO()
    }

    @GetMapping("/{id}/users")
    fun getUsersByResidenceId(@PathVariable id: Long): List<UserDTO> {
        return residenceHandler.getResidenceById(id).users.map { it.toDTO() }
    }

    @PostMapping("/{residenceId}/users")
    fun addUserToResidence(@PathVariable residenceId: Long, @RequestParam userEmail: String): ResidenceDTO {
        return residenceHandler.addUserToResidence(userEmail, residenceId).toDTO()
    }

    @DeleteMapping("/{residenceId}/users")
    fun removeUserFromResidence(@PathVariable residenceId: Long, @RequestParam userEmail: String ): ResidenceDTO {
        return residenceHandler.removeUserFromResidence(userEmail, residenceId).toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteResidence(@PathVariable id: Long) {
        residenceHandler.deleteResidence(id)
    }

    @PutMapping("/{id}")
    fun updateResidence(@PathVariable id: Long, @RequestBody residenceDTO: ResidenceDTO): ResidenceDTO {
        return residenceHandler.updateResidence(id, residenceDTO.toEntity()).toDTO()
    }

}