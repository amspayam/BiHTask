package com.payam.bih.android.features.coffeelist.data

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.data.entity.CoffeeNetworkEntity
import com.payam.bih.android.features.coffeelist.data.local.CoffeeListLocalDataSource
import com.payam.bih.android.features.coffeelist.data.network.CoffeeListRemoteDataSource
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CoffeeListRepositoryImplTest {

    private val localDataSource: CoffeeListLocalDataSource = mockk()
    private val remoteDataSource: CoffeeListRemoteDataSource = mockk()

    private lateinit var repository: CoffeeListRepository

    @Before
    fun setUp() {
        repository = CoffeeListRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun getCoffeeList() = runTest {

        coEvery {
            localDataSource.getCoffeeList()
        } coAnswers {
            listOf(coffeeEntity)
        }

        coEvery {
            remoteDataSource.getCoffeeList()
        } coAnswers {
            networkExpectation
        }

        val actualData = repository.getCoffeeList().first()

        assertEquals(repoExpectation, actualData)


    }

    @Test
    fun updateCoffeeList() = runTest {
        coEvery {
            localDataSource.getCoffeeList()
        } coAnswers {
            listOf(coffeeEntity)
        }

        val actualData = repository.updateCoffeeList()

        assertEquals(repoExpectation, actualData)
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
        private val coffeeNetworkEntity = CoffeeNetworkEntity(
            id = 1,
            title = "Some Title",
            description = "Some Desc",
            image = "Some Image",
            ingredients = listOf("One", "Two")
        )
        val networkExpectation = Result.success(
            listOf(
                coffeeNetworkEntity
            )
        )

        val repoExpectation = Result.success(
            listOf(
                coffeeEntity
            )
        )
    }
}