package com.api.budgeteer.core.users

import com.api.budgeteer.core.Traceable
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
    override var createdAt: Date? = null,
    override var deletedAt : Date? = null,
    override var updatedAt: Date? = null

) : Traceable()