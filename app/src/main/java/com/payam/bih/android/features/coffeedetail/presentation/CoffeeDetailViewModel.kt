package com.payam.bih.android.features.coffeedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.domain.usecase.CoffeeReviewUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.FavoriteCoffeeUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.GetCoffeeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeDetailViewModel @Inject constructor(
    private val getCoffeeDetailUseCase: GetCoffeeDetailUseCase,
    private val favoriteCoffeeUseCase: FavoriteCoffeeUseCase,
    private val coffeeReviewUseCase: CoffeeReviewUseCase
) : ViewModel() {

    private val _coffeeDetailState = MutableStateFlow(CoffeeDetailState(isLoading = true))
    val coffeeDetailState = _coffeeDetailState.asStateFlow()

    fun getCoffeeDetail(coffeeId: Int) {
        viewModelScope.launch {
            getCoffeeDetailUseCase.execute(coffeeId = coffeeId)
                .onSuccess {
                    _coffeeDetailState.value = CoffeeDetailState(coffee = it)
                }
                .onFailure {
                    _coffeeDetailState.value = CoffeeDetailState(
                        error = it.localizedMessage ?: "An unexpected error occurred."
                    )
                }
        }
    }

    fun favoriteCoffee(coffee: CoffeeEntity) {
        _coffeeDetailState.value = CoffeeDetailState(isLoading = true)
        viewModelScope.launch {
            val favCoffee = favoriteCoffeeUseCase.execute(coffeeEntity = coffee)
            _coffeeDetailState.value = CoffeeDetailState(coffee = favCoffee)
        }
    }

    /**
     * The method and its parameters require validation
     * and proper instructions for the user. However, due
     * to a lack of time, I implemented the simplest version.
     * */
    fun submitReview(
        userName: String,
        date: String,
        rating: String,
        desc: String
    ) {

        viewModelScope.launch {
            coffeeReviewUseCase.execute(
                userName = userName, date = date, rating = rating, desc = desc
            )
        }

    }

}