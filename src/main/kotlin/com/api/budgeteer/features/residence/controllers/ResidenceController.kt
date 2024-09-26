package com.api.budgeteer.features.residence.controllers

import com.api.budgeteer.features.residence.ResidenceHandler
import com.api.budgeteer.features.residence.dtos.ResidenceDTO
import com.api.budgeteer.features.residence.dtos.createResidenceDTO
import com.api.budgeteer.features.residence.exceptions.ResidenceCodeException
import com.api.budgeteer.features.residence.exceptions.ResidenceNotFoundException
import com.api.budgeteer.features.users.UserDTO
import com.api.budgeteer.features.users.exceptions.UserEmailNotFoundException
import com.api.budgeteer.features.users.exceptions.UserExceptionCode
import com.api.budgeteer.features.users.exceptions.UserNotFoundException
import io.swagger.models.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/residences")
class ResidenceController(private val residenceHandler: ResidenceHandler) {

    @GetMapping()
    fun getResidences(): List<ResidenceDTO> {
        return residenceHandler.getResidences().map { it.toDTO() }
    }

    @PostMapping()
    fun createResidence(@RequestBody createResidenceDTO: createResidenceDTO): ResponseEntity<ResidenceDTO> {
        return try {
            ResponseEntity(residenceHandler.createResidence(
                createResidenceDTO.name,
                createResidenceDTO.address,
                createResidenceDTO.user
            ).toDTO(), HttpStatus.CREATED)
        } catch (e: UserNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_NOT_FOUND.code))
        }
    }

    @GetMapping("/{id}")
    fun getResidenceById(@PathVariable id: Long): ResponseEntity<ResidenceDTO> {
        return try {
            val residence = residenceHandler.getResidenceById(id)
            ResponseEntity(residence.toDTO(), HttpStatus.OK)
        } catch (e: ResidenceNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(ResidenceCodeException.RESIDENCE_NOT_FOUND.code))
        }
    }

    @GetMapping("/{id}/users")
    fun getUsersByResidenceId(@PathVariable id: Long): ResponseEntity<List<UserDTO>> {
        return try {
            val residence = residenceHandler.getResidenceById(id)
            ResponseEntity(residence.users.map { it.toDTO() }, HttpStatus.OK)
        } catch (e: ResidenceNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(ResidenceCodeException.RESIDENCE_NOT_FOUND.code))
        }
    }

    @PostMapping("/{residenceId}/users")
    fun addUserToResidence(@PathVariable residenceId: Long, @RequestParam userEmail: String): ResponseEntity<ResidenceDTO> {
        return try {
            ResponseEntity(residenceHandler.addUserToResidence(userEmail, residenceId).toDTO(), HttpStatus.CREATED)
        } catch (e: UserEmailNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_EMAIL_NOT_FOUND.code))
        } catch (e: ResidenceNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(ResidenceCodeException.RESIDENCE_NOT_FOUND.code))
        }
    }

    @DeleteMapping("/{residenceId}/users")
    fun removeUserFromResidence(@PathVariable residenceId: Long, @RequestParam userEmail: String ): ResponseEntity<ResidenceDTO>{
        return try {
            ResponseEntity(residenceHandler.removeUserFromResidence(userEmail, residenceId).toDTO(), HttpStatus.OK)
        } catch (e: UserEmailNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(UserExceptionCode.USER_EMAIL_NOT_FOUND.code))
        } catch (e: ResidenceNotFoundException) {
            ResponseEntity(HttpStatus.valueOf(ResidenceCodeException.RESIDENCE_NOT_FOUND.code))
        }
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