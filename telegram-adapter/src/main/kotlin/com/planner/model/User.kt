package com.planner.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Entity represents user of bot
 */
@Entity
@Table(name = "user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "group_id")
    var group: UserGroup? = null

    @Column
    var userId: Long? = null

    @Column
    var chatId: Long? = null

    @Column
    var firstName: String = ""

    @Column
    var lastName: String = ""

    @Column
    var userName: String = ""
}