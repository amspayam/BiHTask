package com.payam.bih.android.features.coffeelist.data.local

import com.payam.bih.android.db.dao.CoffeeDao
import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSource
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSourceImpl
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
class CoffeeListLocalDataSourceImplTest {

    private val coffeeDao: CoffeeDao = mockk()

    private lateinit var coffeeListLocalDataSource: CoffeeListLocalDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        coffeeListLocalDataSource = CoffeeListLocalDataSourceImpl(
            coffeeDao = coffeeDao
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insert() = runTest {
        coEvery {
            coffeeDao.insert(coffeeEntity)
        } just Runs

        val actualData = coffeeListLocalDataSource.insert(
            coffeeEntity
        )

        assertEquals(Unit, actualData)

    }

    @Test
    fun getCoffeeList() = runTest {
        coEvery {
            coffeeDao.getCoffeeList()
        } coAnswers {
            listOf(coffeeEntity)
        }

        val actualData = coffeeListLocalDataSource.getCoffeeList()

        assertEquals(listOf(coffeeEntity), actualData)

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