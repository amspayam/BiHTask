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
class FavoriteCoffeeUseCaseTest {

    private val coffeeDetailRepository: CoffeeDetailRepository = mockk()

    private lateinit var favoriteCoffeeUseCase: FavoriteCoffeeUseCase

    @Before
    fun setUp() {
        favoriteCoffeeUseCase = FavoriteCoffeeUseCase(
            coffeeDetailRepository = coffeeDetailRepository
        )
    }

    @Test
    fun execute() = runTest {
        coEvery {
            coffeeDetailRepository.favorite(coffeeEntity)
        } coAnswers {
            coffeeEntity
        }

        val actualData = coffeeDetailRepository.favorite(
            coffeeEntity
        )

        assertEquals(coffeeEntity, actualData)

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
    }

}