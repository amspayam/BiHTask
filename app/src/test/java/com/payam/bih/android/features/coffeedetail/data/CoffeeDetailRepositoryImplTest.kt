package com.payam.bih.android.features.coffeedetail.data

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeedetail.data.entity.CoffeeReviewRequestEntity
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSource
import com.payam.bih.android.features.coffeedetail.data.network.CoffeeDetailRemoteDataSource
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
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
class CoffeeDetailRepositoryImplTest {

    private val localDataSource: CoffeeDetailLocalDataSource = mockk()
    private val remoteDataSource: CoffeeDetailRemoteDataSource = mockk()

    private lateinit var repository: CoffeeDetailRepository

    @Before
    fun setUp() {
        repository = CoffeeDetailRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun favorite() = runTest {

        coEvery {
            localDataSource.update(
                coffeeEntity
            )
        } coAnswers {
            coffeeEntity
        }

        val actualData = repository.favorite(
            coffeeEntity
        )

        assertEquals(coffeeEntity, actualData)

    }

    @Test
    fun getCoffeeById() = runTest {
        coEvery {
            localDataSource.getCoffeeById(
                coffeeId = coffeeEntity.coffeeId
            )
        } coAnswers {
            coffeeEntity
        }

        val actualData = repository.getCoffeeById(
            coffeeId = coffeeEntity.coffeeId
        )

        assertEquals(repoExpectation, actualData)
    }

    @Test
    fun submitReview() = runTest {
        coEvery {
            remoteDataSource.submitReview(
                coffeeReviewEntity
            )
        } just Runs

        val actualData = repository.submitReview(coffeeReviewRequestEntity = coffeeReviewEntity)

        assertEquals(Unit, actualData)

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
        val coffeeReviewEntity = CoffeeReviewRequestEntity(
            userName = "Some Name",
            date = "Some Date",
            rating = "Some Rating",
            desc = "Some Desc"
        )
        val repoExpectation = Result.success(coffeeEntity)
    }
}