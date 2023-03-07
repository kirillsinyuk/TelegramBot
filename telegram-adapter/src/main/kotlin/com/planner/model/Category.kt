package com.planner.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

/**
 * Entity represents group of products
 */
@Entity
@Table(name = "category")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "group_id")
    var group: UserGroup? = null

    @OneToMany(mappedBy = "category", cascade = [CascadeType.REMOVE])
    var products: List<Product> = emptyList()

    @Column
    var name: String = "Category"
}