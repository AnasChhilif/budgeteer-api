package com.api.budgeteer.users


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>