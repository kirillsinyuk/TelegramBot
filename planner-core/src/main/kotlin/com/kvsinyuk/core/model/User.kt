package com.kvsinyuk.core.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.*

/**
 * Entity represents user of bot
 */
@Entity
@Table(name = "person")
class User {
    @Id
    @GeneratedValue
    var id: UUID = UUID.randomUUID()

    @Column
    var firstName: String? = null

    @Column
    var lastName: String? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var categories: Set<Category> = emptySet()
}
