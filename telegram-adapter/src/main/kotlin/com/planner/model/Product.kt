package com.planner.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

/**
 * Entity represents spending
 */
@Entity
@Table(name = "product")
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null

    @Column
    var name: String = ""

    /**
     * Price in RUB
     */
    @Column
    var price: Float = 0.0f

    @Column
    var description: String? = null

    @Column
    var deleted: Boolean = false

    @ManyToOne
    @JoinColumn(name = "user_id")
    var addedBy: User? = null

    @CreatedDate
    var createdAt: Instant = Instant.now()
}