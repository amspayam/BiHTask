package com.payam.bih.android.features.coffeelist.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetCoffeeListUseCaseTest {

    private var coffeeListRepository: CoffeeListRepository = mockk()

    private lateinit var getCoffeeListUseCase: GetCoffeeListUseCase

    @Before
    fun setUp() {
        getCoffeeListUseCase = GetCoffeeListUseCase(
            coffeeListRepository = coffeeListRepository
        )
    }

    @Test
    fun execute() = runTest {
        coEvery {
            coffeeListRepository.getCoffeeList()
        } returns flow {
            emit(repoExpectation)
        }

        val actualData = getCoffeeListUseCase.execute().first()

        assertEquals(useCaseExpectation, actualData)

    }

    companion object {
        val repoExpectation = Result.success(
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

    }

}