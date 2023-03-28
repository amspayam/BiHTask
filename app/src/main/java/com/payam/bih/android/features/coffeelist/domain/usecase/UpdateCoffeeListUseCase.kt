package com.payam.bih.android.features.coffeelist.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import javax.inject.Inject

class UpdateCoffeeListUseCase @Inject constructor(
    private val repository: CoffeeListRepository
) {

    suspend fun execute(): Result<List<CoffeeEntity>> {
        return repository.updateCoffeeList()
    }

}