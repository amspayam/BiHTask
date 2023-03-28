package com.payam.bih.android.features.coffeedetail.data.network

import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity

interface CoffeeDetailRemoteDataSource {
    suspend fun submitReview(
        reviewRequestEntity: CoffeeReviewRequestEntity
    )
}