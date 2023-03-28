package com.payam.bih.android.features.coffeedetail.data

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSource
import com.payam.bih.android.features.coffeedetail.data.network.CoffeeDetailRemoteDataSource
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import javax.inject.Inject

class CoffeeDetailRepositoryImpl @Inject constructor(
    private val localDataSource: CoffeeDetailLocalDataSource,
    private val remoteDataSource: CoffeeDetailRemoteDataSource
) : CoffeeDetailRepository {
    override suspend fun favorite(coffeeEntity: CoffeeEntity): CoffeeEntity {
        return localDataSource.update(coffeeEntity = coffeeEntity)
    }

    override suspend fun getCoffeeById(coffeeId: Int) = runCatching {
        localDataSource.getCoffeeById(coffeeId = coffeeId)
    }

    override suspend fun submitReview(coffeeReviewRequestEntity: CoffeeReviewRequestEntity) {
        remoteDataSource.submitReview(
            reviewRequestEntity = coffeeReviewRequestEntity
        )
    }
}