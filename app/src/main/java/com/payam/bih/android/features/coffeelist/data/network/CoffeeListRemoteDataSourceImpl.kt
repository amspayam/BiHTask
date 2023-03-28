package com.payam.bih.android.features.coffeelist.data.network

import com.payam.bih.android.features.coffeelist.data.api.CoffeeListApiService
import javax.inject.Inject

class CoffeeListRemoteDataSourceImpl @Inject constructor(
    private val api: CoffeeListApiService
) : CoffeeListRemoteDataSource {

    override suspend fun getCoffeeList() = runCatching {
        api.getCoffeeList()
    }

}