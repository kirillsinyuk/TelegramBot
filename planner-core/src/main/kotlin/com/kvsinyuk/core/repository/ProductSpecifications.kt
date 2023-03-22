package com.kvsinyuk.core.repository

import com.kvsinyuk.core.model.Category_
import com.kvsinyuk.core.model.Product_
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
    Specification { root, _, _ -> root.get(Product_.category).get(Category_.id).`in`(categoryIds) }
