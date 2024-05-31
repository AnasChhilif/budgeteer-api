package com.api.budgeteer.features.users

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.residences.Residence
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

    @Column(unique = true)
    var email: String,

    @ManyToOne
    @JoinColumn(name = "residence_id")
    var residence: Residence? = null,


    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
    override var deletedAt: LocalDateTime? = null

) : TraceableEntity(){
    constructor(firstName: String, lastName: String, email: String) : this(0, firstName, lastName, email,null, LocalDateTime.now(), LocalDateTime.now())
    constructor() : this(0, "userFirstName", "userLastName", "userEmail@gmail.com",null, LocalDateTime.now(), LocalDateTime.now())
}