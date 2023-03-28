package com.payam.bih.android.features.coffeelist.presentation

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.domain.usecase.GetCoffeeListUseCase
import com.payam.bih.android.features.coffeelist.domain.usecase.UpdateCoffeeListUseCase
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CoffeeListViewModelTest {

    @RelaxedMockK
    private val getCoffeeListUseCase: GetCoffeeListUseCase = mockk()
    private val updateCoffeeListUseCase: UpdateCoffeeListUseCase = mockk()

    private lateinit var viewModel: CoffeeListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CoffeeListViewModel(
            getCoffeeListUseCase = getCoffeeListUseCase,
            updateCoffeeListUseCase = updateCoffeeListUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute getCoffeeListUseCase, success response`() = runTest {

        every {
            getCoffeeListUseCase.execute()
        } returns flowOf(useCaseExpectation)

        // Action
        viewModel.getCoffeeList()

        assertEquals(vieModelExpectation, viewModel.coffeeListState.value)

        verify {
            getCoffeeListUseCase.execute()
        }

    }

    @Test
    fun `execute getCoffeeListUseCase, failure response`() = runTest {

        coEvery {
            getCoffeeListUseCase.execute()
        } returns flow {
            emit(useCaseFailureExpectation)
        }

        // Action
        viewModel.getCoffeeList()

        assertEquals(vieModelFailureExpectation, viewModel.coffeeListState.value)

    }

    @Test
    fun `execute updateCoffeeListUseCase, success response`() = runTest {

        coEvery {
            updateCoffeeListUseCase.execute()
        } coAnswers {
            useCaseExpectation
        }

        // Action
        viewModel.updateList()

        assertEquals(vieModelExpectation, viewModel.coffeeListState.value)

        coVerify {
            updateCoffeeListUseCase.execute()
        }

    }

    companion object {
        val useCaseExpectation = Result.success(
            listOf(
                CoffeeEntity(
                    coffeeId = 1,
                    coffeeTitle = "Some Title",
                    coffeeDescription = "Some Desc",
                    coffeeImageLink = "Some Image",
                    ingredients = listOf("Some Ingredient", "Some other Ingredient"),
                    isFavorite = false
                ),
                CoffeeEntity(
                    coffeeId = 2,
                    coffeeTitle = "Some Title",
                    coffeeDescription = "Some Desc",
                    coffeeImageLink = "Some Image",
                    ingredients = listOf("Some Ingredient", "Some other Ingredient"),
                    isFavorite = false
                )
            )
        )

        val useCaseFailureExpectation = Result.failure<List<CoffeeEntity>>(
            Throwable(
                "Some Error"
            )
        )

        val vieModelExpectation =
            CoffeeListState(coffeeList = useCaseExpectation.getOrNull())

        val vieModelFailureExpectation =
            CoffeeListState(
                error = "Some Error"
            )
    }

}