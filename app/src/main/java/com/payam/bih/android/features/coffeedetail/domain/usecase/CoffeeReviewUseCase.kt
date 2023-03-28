package com.payam.bih.android.features.coffeedetail.domain.usecase

import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import javax.inject.Inject

class CoffeeReviewUseCase @Inject constructor(
    private val repository: CoffeeDetailRepository
) {

    suspend fun execute(
        userName: String,
        date: String,
        rating: String,
        desc: String
    ) {
        repository.submitReview(
            CoffeeReviewRequestEntity(
                userName = userName, date = date, rating = rating, desc = desc
            )
        )
    }

}