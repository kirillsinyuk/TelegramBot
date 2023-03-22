package com.kvsinyuk.core.model

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
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "category_id")
    lateinit var category: Category

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
    @JoinColumn(name = "author_id")
    lateinit var author: User

    @CreatedDate
    var createdAt: Instant = Instant.now()
}
