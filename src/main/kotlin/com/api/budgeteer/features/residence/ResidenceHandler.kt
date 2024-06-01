package com.api.budgeteer.features.residence

interface ResidenceHandler {

    fun getResidences(): List<Residence>
    fun getResidenceById(id: Long): Residence
    fun addUserToResidence(userEmail: String, residenceId: Long): Residence
    fun removeUserFromResidence(userEmail: String, residenceId: Long): Residence
    fun createResidence(name: String, address: String, userId: Long): Residence
    fun deleteResidence(id: Long): Unit
    fun updateResidence(id: Long, residence: Residence): Residence
}