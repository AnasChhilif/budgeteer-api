package com.api.budgeteer.features.residence

import com.api.budgeteer.features.residence.dtos.ResidenceDTO
import com.api.budgeteer.features.residence.dtos.createResidenceDTO
import com.api.budgeteer.features.users.UserDTO
import com.api.budgeteer.features.users.toDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/residences")
class ResidenceController(private val residenceHandler: ResidenceHandler) {

    @GetMapping()
    fun getResidences(): List<ResidenceDTO> {
        return residenceHandler.getResidences().map { toDTO(it) }
    }

    @PostMapping()
    fun createResidence(@RequestBody createResidenceDTO: createResidenceDTO): ResidenceDTO {
        return toDTO(residenceHandler.createResidence(createResidenceDTO.name, createResidenceDTO.address, createResidenceDTO.user))
    }

    @GetMapping("/{id}")
    fun getResidenceById(@PathVariable id: Long): ResidenceDTO {
        return toDTO(residenceHandler.getResidenceById(id))
    }

    @GetMapping("/{id}/users")
    fun getUsersByResidenceId(@PathVariable id: Long): List<UserDTO> {
        return residenceHandler.getResidenceById(id).users.map { toDTO(it) }
    }

    @PostMapping("/{residenceId}/users")
    fun addUserToResidence(userId: Long, @PathVariable residenceId: Long): ResidenceDTO {
        return toDTO(residenceHandler.addUserToResidence(userId, residenceId))
    }

    @DeleteMapping("/{residenceId}/users/{userId}")
    fun removeUserFromResidence(@PathVariable userId: Long, @PathVariable residenceId: Long): ResidenceDTO {
        return toDTO(residenceHandler.removeUserFromResidence(userId, residenceId))
    }

    @DeleteMapping("/{id}")
    fun deleteResidence(@PathVariable id: Long) {
        residenceHandler.deleteResidence(id)
    }

    @PutMapping("/{id}")
    fun updateResidence(@PathVariable id: Long, @RequestBody residenceDTO: ResidenceDTO): ResidenceDTO {
        return toDTO(residenceHandler.updateResidence(id, toEntity(residenceDTO)))
    }

}