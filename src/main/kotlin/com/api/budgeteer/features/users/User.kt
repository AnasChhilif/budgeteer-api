package com.api.budgeteer.features.users

import com.api.budgeteer.core.entities.TraceableEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var firstName:String,
    var lastName: String,
    var email: String,
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
    override var deletedAt: LocalDateTime? = null

) : TraceableEntity(){
    constructor(firstName: String, lastName: String, email: String) : this(0, firstName, lastName, email, LocalDateTime.now(), LocalDateTime.now())
    constructor() : this(0, "userFirstName", "userLastName", "userEmail@gmail.com", LocalDateTime.now(), LocalDateTime.now())
}