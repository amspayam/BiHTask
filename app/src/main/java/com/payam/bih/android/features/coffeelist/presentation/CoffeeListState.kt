package com.payam.bih.android.features.coffeelist.presentation

import com.payam.bih.android.db.entity.CoffeeEntity

data class CoffeeListState(
    val isLoading: Boolean = false,
    val coffeeList: List<CoffeeEntity>? = null,
    val error: String = ""
)
