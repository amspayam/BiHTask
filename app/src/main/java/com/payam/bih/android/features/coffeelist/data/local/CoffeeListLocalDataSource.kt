package com.payam.bih.android.features.coffeelist.data.local

import com.payam.bih.android.db.entity.CoffeeEntity


interface CoffeeListLocalDataSource {
    suspend fun insert(coffeeEntity: CoffeeEntity)
    suspend fun getCoffeeList(): List<CoffeeEntity>
}