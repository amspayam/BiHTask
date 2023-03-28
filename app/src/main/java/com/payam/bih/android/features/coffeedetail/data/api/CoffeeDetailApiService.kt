package com.payam.bih.android.features.coffeedetail.data.api

import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CoffeeDetailApiService {

    /**
     * The Response class of Retrofit needs to handle this API and its errors,
     * but I didn't implement the error handler due to a lack of time.
     * */
    @POST("coffee/hot")
    suspend fun coffeeReview(@Body coffeeReviewRequestEntity: CoffeeReviewRequestEntity)

}