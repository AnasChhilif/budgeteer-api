package com.api.budgeteer.features.monthlydata

import com.api.budgeteer.core.entities.TraceableEntity
import com.api.budgeteer.features.monthlydata.DTOs.MonthlyDataDTO
import com.api.budgeteer.features.residence.Residence
import com.api.budgeteer.features.users.User
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "monthly-data")
data class MonthlyData (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "start_date")
    val startDate: LocalDate,

    @Column(name = "end_date")
    val endDate: LocalDate,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "residence_id")
    val residence: Residence,

    @Column(name = "amount_spent")
    var amountSpent: Double = 0.0,

): TraceableEntity(){
    constructor(startDate: LocalDate, endDate: LocalDate, user: User, residence: Residence, amountSpent: Double) : this(0, startDate, endDate, user, residence, amountSpent)
    constructor() : this(LocalDate.now(), LocalDate.now(), User(), Residence(), 0.0)

    fun toDTO() = MonthlyDataDTO(id, startDate.toString(), endDate.toString(), user.id, residence.id, amountSpent)

    override fun toString(): String {
        return "MonthlyData(id=$id, startDate='$startDate', endDate='$endDate', user='$user', residence='$residence')"
    }
}