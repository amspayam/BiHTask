package com.payam.bih.android.features.coffeelist.data.network

import com.payam.bih.android.features.coffeelist.data.entity.CoffeeNetworkEntity

interface CoffeeListRemoteDataSource {
    suspend fun getCoffeeList(): Result<List<CoffeeNetworkEntity>>
}