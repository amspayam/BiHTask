package com.payam.bih.android.features.coffeelist.domain.repository

import com.payam.bih.android.db.entity.CoffeeEntity
import kotlinx.coroutines.flow.Flow


interface CoffeeListRepository {
    fun getCoffeeList(): Flow<Result<List<CoffeeEntity>>>
    suspend fun updateCoffeeList(): Result<List<CoffeeEntity>>
}