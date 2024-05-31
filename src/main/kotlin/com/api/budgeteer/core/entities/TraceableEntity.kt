package com.api.budgeteer.core.entities

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class TraceableEntity {

    @Column(nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    var deletedAt: LocalDateTime? = null
}