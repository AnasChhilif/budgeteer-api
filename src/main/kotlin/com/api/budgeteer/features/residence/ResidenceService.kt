package com.api.budgeteer.features.residence

import com.api.budgeteer.features.residence.exceptions.ResidenceNotFoundException
import com.api.budgeteer.features.users.UserHandler
import com.api.budgeteer.features.users.exceptions.UserNotFoundException

class ResidenceService(private val residenceRepository: ResidenceRepository, private val userHandler: UserHandler) : ResidenceHandler {
    override fun getResidences(): List<Residence> {
        return residenceRepository.findAll()
    }

    override fun getResidenceById(id: Long): Residence {
        return residenceRepository.findById(id).orElseThrow { ResidenceNotFoundException(id) }
    }

    override fun createResidence(name: String, address: String, userId: Long): Residence {
        try {
            val user = userHandler.getUserById(userId)
            val residence = Residence(name, address, listOf(user))
            return residenceRepository.save(residence)
        } catch (e: UserNotFoundException) {
            throw UserNotFoundException(userId)
        }
    }

    override fun deleteResidence(id: Long) {
        residenceRepository.deleteById(id)
    }

    override fun updateResidence(id: Long, residence: Residence): Residence {
        val existingResidence = residenceRepository.findById(id).orElseThrow { ResidenceNotFoundException(id) }
        val updatedResidence = existingResidence.copy(
            name = residence.name,
            address = residence.address,
            users = residence.users
        )

        return residenceRepository.save(updatedResidence)
    }
}