package com.payam.bih.android.features.coffeedetail.data.local

import com.payam.bih.android.db.dao.CoffeeDao
import com.payam.bih.android.db.entity.CoffeeEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
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
class CoffeeDetailLocalDataSourceImplTest {

    private val coffeeDao: CoffeeDao = mockk()

    private lateinit var coffeeDetailLocalDataSource: CoffeeDetailLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        coffeeDetailLocalDataSource = CoffeeDetailLocalDataSourceImpl(
            coffeeDao = coffeeDao
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun update() = runTest {

        coEvery {
            coffeeDao.update(coffeeEntity)
        } just Runs

        coEvery {
            coffeeDao.getCoffeeById(coffeeId = 1)
        } coAnswers {
            coffeeEntity
        }

        val actualData = coffeeDetailLocalDataSource.update(
            coffeeEntity
        )

        assertEquals(coffeeEntity, actualData)

    }

    @Test
    fun getCoffeeById() = runTest {
        coEvery {
            coffeeDao.getCoffeeById(coffeeId = 1)
        } coAnswers {
            coffeeEntity
        }

        val actualData = coffeeDetailLocalDataSource.getCoffeeById(
            coffeeId = 1
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