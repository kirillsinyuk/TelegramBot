package com.planner.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

/**
 * Entity represents group of users with the same budget
 */
@Entity
@Table(name = "user_group")
class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
    @JoinColumn(name = "admin_id")
    var admin: User? = null

    @OneToMany(mappedBy = "group")
    var users: Set<User> = emptySet()

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL])
    var categories: Set<Category> = emptySet()
}
