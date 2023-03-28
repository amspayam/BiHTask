package com.payam.bih.android.features.coffeelist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payam.bih.android.features.coffeelist.domain.usecase.GetCoffeeListUseCase
import com.payam.bih.android.features.coffeelist.domain.usecase.UpdateCoffeeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeListViewModel @Inject constructor(
    private val getCoffeeListUseCase: GetCoffeeListUseCase,
    private val updateCoffeeListUseCase: UpdateCoffeeListUseCase
) : ViewModel() {

    private val _coffeeListState = MutableStateFlow(CoffeeListState(isLoading = true))
    val coffeeListState = _coffeeListState.asStateFlow()

    fun getCoffeeList() {
        _coffeeListState.value = CoffeeListState(isLoading = true)

        getCoffeeListUseCase.execute()
            .onEach { result ->
                result.onSuccess {
                    _coffeeListState.value = CoffeeListState(
                        coffeeList = it
                    )
                }.onFailure {
                    _coffeeListState.value = CoffeeListState(
                        error = it.localizedMessage ?: "An unexpected error occurred."
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun updateList() {
        viewModelScope.launch {
            updateCoffeeListUseCase.execute()
                .onSuccess {
                    _coffeeListState.value = CoffeeListState(
                        coffeeList = it
                    )
                }
        }
    }

}