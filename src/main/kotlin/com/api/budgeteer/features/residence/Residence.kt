package com.api.budgeteer.features.residence

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.users.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "residences")
data class Residence (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true)
    val name: String,

    val address: String,

    @OneToMany(mappedBy = "residence", fetch = FetchType.EAGER)
    var users: List<User>,


) : TraceableEntity(){
    constructor(name: String, address: String, users: List<User>) : this(0, name, address, users)
    constructor() : this(0, "residenceName", "residenceAddress", listOf(User(), User()))


    override fun toString(): String {
        return "Residence(id=$id, name='$name', address='$address')"
    }
}