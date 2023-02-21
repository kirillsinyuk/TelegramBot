package com.planner.repository

import com.planner.model.Category_
import com.planner.model.Product_
import org.springframework.data.jpa.domain.Specification
import java.time.Instant

fun isBetweenDates(from: Instant, to: Instant) =
    Specification { root, _, cb -> cb.between(root.get(Product_.createdAt), from, to) }

fun inCategories(categoryIds: Set<Long>) =
    Specification { root, _, cb -> root.get(Product_.category).get(Category_.id).`in`(categoryIds) }
