package com.planner.repository

import com.planner.model.Category_
import com.planner.model.Product_
import org.springframework.data.jpa.domain.Specification
import java.time.Instant

fun isAfter(date: Instant?) =
    Specification { root, _, cb ->
        date?.let { cb.greaterThanOrEqualTo(root.get(Product_.createdAt), date) } ?: cb.conjunction()
    }

fun isBefore(date: Instant?) =
    Specification { root, _, cb ->
        date?.let { cb.lessThanOrEqualTo(root.get(Product_.createdAt), date) } ?: cb.conjunction()
    }

fun isNotDeleted() =
    Specification { root, _, cb -> cb.equal(root.get(Product_.deleted), false) }

fun inCategories(categoryIds: Set<Long>) =
    Specification { root, _, cb -> root.get(Product_.category).get(Category_.id).`in`(categoryIds) }
