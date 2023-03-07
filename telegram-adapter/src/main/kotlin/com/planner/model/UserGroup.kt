package com.planner.model

import com.planner.model.Category
import com.planner.model.User
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
@Table(name = "band")
class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne
    @JoinColumn(name = "admin_id")
    var admin: User? = null

    @OneToMany(mappedBy = "group")
    var users: Set<User> = emptySet()

    @OneToMany(mappedBy = "group", cascade = [CascadeType.REMOVE])
    var categories: Set<Category> = emptySet()

}