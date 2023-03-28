package com.payam.bih.android.features.coffeelist.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoffeeListUseCase @Inject constructor(
    private val coffeeListRepository: CoffeeListRepository
) {

    fun execute(): Flow<Result<List<CoffeeEntity>>> {
        return coffeeListRepository.getCoffeeList()
    }

}