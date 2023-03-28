package com.payam.bih.android.features.coffeedetail.domain.repository

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity

interface CoffeeDetailRepository {
    suspend fun favorite(coffeeEntity: CoffeeEntity): CoffeeEntity
    suspend fun getCoffeeById(coffeeId: Int): Result<CoffeeEntity>
    suspend fun submitReview(coffeeReviewRequestEntity: CoffeeReviewRequestEntity)
}