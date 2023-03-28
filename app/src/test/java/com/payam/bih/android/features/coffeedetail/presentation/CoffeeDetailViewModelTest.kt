package com.payam.bih.android.features.coffeedetail.presentation

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.domain.usecase.CoffeeReviewUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.FavoriteCoffeeUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.GetCoffeeDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CoffeeDetailViewModelTest {

    private val getCoffeeDetailUseCase: GetCoffeeDetailUseCase = mockk()
    private val favoriteCoffeeUseCase: FavoriteCoffeeUseCase = mockk()
    private val coffeeReviewUseCase: CoffeeReviewUseCase = mockk()

    private lateinit var viewModel: CoffeeDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CoffeeDetailViewModel(
            getCoffeeDetailUseCase = getCoffeeDetailUseCase,
            favoriteCoffeeUseCase = favoriteCoffeeUseCase,
            coffeeReviewUseCase = coffeeReviewUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute getCoffeeDetailUseCase, success response`() = runTest {

        coEvery {
            getCoffeeDetailUseCase.execute(coffeeId = coffeeEntity.coffeeId)
        } coAnswers {
            useCaseExpectation
        }

        viewModel.getCoffeeDetail(coffeeId = coffeeEntity.coffeeId)

        assertEquals(getCoffeeDetailExpectation, viewModel.coffeeDetailState.value)

    }

    @Test
    fun `execute favoriteCoffeeUseCase, success response`() = runTest {

        coEvery {
            favoriteCoffeeUseCase.execute(coffeeEntity = coffeeEntity)
        } coAnswers {
            coffeeEntity
        }

        viewModel.favoriteCoffee(coffee = coffeeEntity)

        assertEquals(getCoffeeDetailExpectation, viewModel.coffeeDetailState.value)

    }

    companion object {
        val coffeeEntity = CoffeeEntity(
            coffeeId = 1,
            coffeeTitle = "Some Title",
            coffeeDescription = "Some desc",
            coffeeImageLink = "Some Link",
            ingredients = listOf("One", "Two"),
            isFavorite = true
        )
        val useCaseExpectation = Result.success(coffeeEntity)

        val getCoffeeDetailExpectation = CoffeeDetailState(
            coffee = coffeeEntity
        )
    }

}