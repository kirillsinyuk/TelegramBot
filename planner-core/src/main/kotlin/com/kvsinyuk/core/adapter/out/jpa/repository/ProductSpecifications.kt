package com.kvsinyuk.core.adapter.out.jpa.repository

import com.kvsinyuk.core.adapter.out.jpa.entity.Category_
import com.kvsinyuk.core.adapter.out.jpa.entity.Product_
import org.springframework.data.jpa.domain.Specification
import java.time.Instant
import java.util.*

fun isAfter(date: Instant?) = Specification { root, _, cb ->
    date?.let { cb.greaterThanOrEqualTo(root.get(Product_.createdAt), date) } ?: cb.conjunction()
}

fun isBefore(date: Instant?) = Specification { root, _, cb ->
    date?.let { cb.lessThanOrEqualTo(root.get(Product_.createdAt), date) } ?: cb.conjunction()
}

fun isNotDeleted() = Specification { root, _, cb ->
    cb.equal(root.get(Product_.deleted), false)
}

fun inCategories(categoryIds: Set<UUID>) = Specification { root, _, _ ->
    root.get(Product_.category).get(Category_.id).`in`(categoryIds)
}
