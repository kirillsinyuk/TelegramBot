package com.kvsinyuk.core.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

/**
 * Entity represents category of products
 */
@Entity
@Table(name = "category")
class Category {

    // Move to sequence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var user: User

    @OneToMany(mappedBy = "category", cascade = [CascadeType.REMOVE])
    var products: List<Product> = emptyList()

    @Column
    var name: String = "Category"
}