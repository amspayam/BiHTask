package com.payam.bih.android.features.coffeedetail.data.local

import com.payam.bih.android.db.entity.CoffeeEntity

interface CoffeeDetailLocalDataSource {
    suspend fun update(coffeeEntity: CoffeeEntity): CoffeeEntity
    suspend fun getCoffeeById(coffeeId: Int): CoffeeEntity
}