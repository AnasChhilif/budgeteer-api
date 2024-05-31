package com.api.budgeteer.features.residence

import com.api.budgeteer.features.users.User
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
    fun createResidence(@RequestBody residenceDTO: ResidenceDTO): ResidenceDTO {
        return toDTO(residenceHandler.createResidence(residenceDTO.name, residenceDTO.address, residenceDTO.users.first()))
    }

    @GetMapping("/{id}")
    fun getResidenceById(id: Long): ResidenceDTO {
        return toDTO(residenceHandler.getResidenceById(id))
    }

    @GetMapping("/{id}/users")
    fun getUsersByResidenceId(id: Long): List<UserDTO> {
        return residenceHandler.getResidenceById(id).users.map { toDTO(it) }
    }

    @PostMapping("/{id}/users")
    fun addUserToResidence(userId: Long, residenceId: Long): ResidenceDTO {
        return toDTO(residenceHandler.addUserToResidence(userId, residenceId))
    }

    @DeleteMapping("/{id}/users")
    fun removeUserFromResidence(userId: Long, residenceId: Long): ResidenceDTO {
        return toDTO(residenceHandler.removeUserFromResidence(userId, residenceId))
    }

    @DeleteMapping("/{id}")
    fun deleteResidence(id: Long) {
        residenceHandler.deleteResidence(id)
    }

    @PostMapping("/{id}")
    fun updateResidence(id: Long, @RequestBody residenceDTO: ResidenceDTO): ResidenceDTO {
        return toDTO(residenceHandler.updateResidence(id, toEntity(residenceDTO)))
    }

}