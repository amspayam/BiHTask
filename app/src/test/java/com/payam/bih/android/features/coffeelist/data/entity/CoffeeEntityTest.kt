package com.payam.bih.android.features.coffeelist.data.entity

import com.payam.bih.android.db.entity.CoffeeEntity
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoffeeEntityTest {

    @Test
    fun `get coffee entity from server, convert to model successfully`() {
        val expectation = CoffeeEntity(
            coffeeId = 1,
            coffeeTitle = "Some Title",
            coffeeDescription = "Some Desc",
            coffeeImageLink = "Some Image",
            ingredients = listOf("Some Ingredient", "Some other Ingredient"),
            isFavorite = false
        )

        val actualData = CoffeeNetworkEntity(
            id = 1,
            title = "Some Title",
            description = "Some Desc",
            image = "Some Image",
            ingredients = listOf("Some Ingredient", "Some other Ingredient")
        ).toModel()

        assertEquals(expectation, actualData)

    }

}