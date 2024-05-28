package com.api.budgeteer.features.users

import com.api.budgeteer.core.entities.TraceableEntity
import jakarta.persistence.*
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

) : TraceableEntity()