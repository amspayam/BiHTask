package com.payam.bih.android.features.coffeelist.data.api

import com.payam.bih.android.features.coffeelist.data.entity.CoffeeNetworkEntity
import retrofit2.http.GET

interface CoffeeListApiService {

    @GET("coffee/hot")
    suspend fun getCoffeeList(): List<CoffeeNetworkEntity>

}