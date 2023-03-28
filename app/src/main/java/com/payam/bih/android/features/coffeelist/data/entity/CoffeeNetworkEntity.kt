package com.payam.bih.android.features.coffeelist.data.entity

import androidx.annotation.Keep
import com.payam.bih.android.db.entity.CoffeeEntity

@Keep
data class CoffeeNetworkEntity(
    val id: Int?,
    val title: String?,
    val description: String?,
    val image: String?,
    val ingredients: List<String>?
)

fun CoffeeNetworkEntity.toModel() = CoffeeEntity(
    coffeeId = id ?: 0,
    coffeeTitle = title ?: "",
    coffeeDescription = description ?: "",
    ingredients = ingredients ?: listOf(),
    coffeeImageLink = image ?: ""
)