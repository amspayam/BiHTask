package com.payam.bih.android.features.coffeedetail.domain.usecase

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetCoffeeDetailUseCaseTest {

    private val coffeeDetailRepository: CoffeeDetailRepository = mockk()

    private lateinit var getCoffeeDetailUseCase: GetCoffeeDetailUseCase

    @Before
    fun setUp() {
        getCoffeeDetailUseCase = GetCoffeeDetailUseCase(
            coffeeDetailRepository = coffeeDetailRepository
        )
    }

    @Test
    fun execute() = runTest {
        coEvery {
            coffeeDetailRepository.getCoffeeById(coffeeId = coffeeEntity.coffeeId)
        } coAnswers {
            useCaseExpectation
        }

        val actualData = coffeeDetailRepository.getCoffeeById(
            coffeeId = coffeeEntity.coffeeId
        )

        assertEquals(useCaseExpectation, actualData)
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
    }
}