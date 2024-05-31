package com.api.budgeteer.features.residences

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

    @OneToMany(mappedBy = "residence")
    val users: List<User>,

    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var updatedAt: LocalDateTime = LocalDateTime.now(),
    override var deletedAt: LocalDateTime? = null


) : TraceableEntity(){
    constructor(name: String, address: String, users: List<User>) : this(0, name, address, users)
    constructor() : this(0, "residenceName", "residenceAddress", listOf(User(), User()))
}