package com.api.budgeteer.features.residence

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.residence.dtos.ResidenceDTO
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

    @OneToMany(mappedBy = "residence", fetch = FetchType.EAGER,cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var users: List<User>,


) : TraceableEntity(){
    constructor(name: String, address: String, users: List<User>) : this(0, name, address, users)
    constructor(id: Long?, name: String, address: String) : this(id?: 0, name, address, listOf<User>())
    constructor() : this(0, "residenceName", "residenceAddress", listOf(User(), User()))

    fun toDTO() = ResidenceDTO(id, name, address, users.map { it.id })

    override fun toString(): String {
        return "Residence(id=$id, name='$name', address='$address')"
    }
}