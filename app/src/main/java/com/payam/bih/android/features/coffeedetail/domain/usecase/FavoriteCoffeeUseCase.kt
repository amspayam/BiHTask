package com.payam.bih.android.features.coffeedetail.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import javax.inject.Inject

class FavoriteCoffeeUseCase @Inject constructor(
    private val coffeeDetailRepository: CoffeeDetailRepository
) {

    suspend fun execute(coffeeEntity: CoffeeEntity): CoffeeEntity {
        coffeeEntity.isFavorite = !coffeeEntity.isFavorite
        return coffeeDetailRepository.favorite(coffeeEntity = coffeeEntity)
    }

}