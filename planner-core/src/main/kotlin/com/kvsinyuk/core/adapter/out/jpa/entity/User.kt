package com.kvsinyuk.core.adapter.out.jpa.entity

import com.kvsinyuk.core.adapter.out.jpa.entity.Category
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
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
    var id: UUID = UUID.randomUUID()

    @Column
    var firstName: String? = null

    @Column
    var lastName: String? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var categories: Set<Category> = emptySet()
}
