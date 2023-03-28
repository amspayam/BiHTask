package com.payam.bih.android.features.coffeedetail.data.network

import com.payam.bih.android.features.coffeedetail.data.api.CoffeeDetailApiService
import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity
import javax.inject.Inject

class CoffeeDetailRemoteDataSourceImpl @Inject constructor(
    private val api: CoffeeDetailApiService
) : CoffeeDetailRemoteDataSource {
    override suspend fun submitReview(reviewRequestEntity: CoffeeReviewRequestEntity) {
        api.coffeeReview(
            coffeeReviewRequestEntity = reviewRequestEntity
        )
    }
}