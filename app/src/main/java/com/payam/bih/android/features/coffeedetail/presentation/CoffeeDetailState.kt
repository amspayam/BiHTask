package com.payam.bih.android.features.coffeedetail.presentation

import com.payam.bih.android.db.entity.CoffeeEntity

data class CoffeeDetailState(
    val isLoading: Boolean = false,
    val coffee: CoffeeEntity? = null,
    val error: String = ""
)
