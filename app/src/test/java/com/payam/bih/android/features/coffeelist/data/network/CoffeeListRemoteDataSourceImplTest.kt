package com.payam.bih.android.features.coffeelist.data.network

import com.payam.bih.android.features.coffeelist.data.api.CoffeeListApiService
import com.payam.bih.android.features.coffeelist.data.entity.CoffeeNetworkEntity
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
class CoffeeListRemoteDataSourceImplTest {

    private val api: CoffeeListApiService = mockk()

    private lateinit var remoteDataSource: CoffeeListRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = CoffeeListRemoteDataSourceImpl(
            api = api
        )
    }

    @Test
    fun getCoffeeList() = runTest {
        coEvery {
            api.getCoffeeList()
        } coAnswers {
            listOf(coffeeNetworkEntity)
        }

        val actualData = remoteDataSource.getCoffeeList()

        assertEquals(
            remoteDataSourceExpectation,
            actualData
        )

    }

    companion object {
        val coffeeNetworkEntity = CoffeeNetworkEntity(
            id = 1,
            title = "Some Title",
            description = "Some Desc",
            image = "Some Image",
            ingredients = listOf("One", "Two")
        )

        val remoteDataSourceExpectation = Result.success(
            listOf(coffeeNetworkEntity)
        )
    }
}