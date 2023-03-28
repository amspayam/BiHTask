package com.payam.bih.android.features.coffeedetail.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import javax.inject.Inject

class GetCoffeeDetailUseCase @Inject constructor(
    private val coffeeDetailRepository: CoffeeDetailRepository
) {

    suspend fun execute(coffeeId: Int): Result<CoffeeEntity> {
        return coffeeDetailRepository.getCoffeeById(coffeeId = coffeeId)
    }

}