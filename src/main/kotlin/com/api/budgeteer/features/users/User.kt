package com.api.budgeteer.features.users

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.residence.Residence
import jakarta.persistence.*

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



    ) : TraceableEntity(){
    constructor(firstName: String, lastName: String, email: String) : this(0, firstName, lastName, email,null)
    constructor() : this(0, "userFirstName", "userLastName", "userEmail@gmail.com",null)

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', email='$email')"
    }
}