package com.api.budgeteer.features.residence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResidenceRepository : JpaRepository<Residence, Long>