package com.payam.bih.android.features.coffeelist.data

import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.features.coffeelist.data.entity.toModel
import com.payam.bih.android.features.coffeelist.data.local.CoffeeListLocalDataSource
import com.payam.bih.android.features.coffeelist.data.network.CoffeeListRemoteDataSource
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoffeeListRepositoryImpl @Inject constructor(
    private val localDataSource: CoffeeListLocalDataSource,
    private val remoteDataSource: CoffeeListRemoteDataSource
) : CoffeeListRepository {

    /**
     * It is better to delete the database records with each API call.
     * However, certain values such as "favorites" are only stored locally.
     * If the database records are deleted,
     * then all the saved favorites will also be erased.
     * */
    override fun getCoffeeList(): Flow<Result<List<CoffeeEntity>>> = flow {
        if (localDataSource.getCoffeeList().isEmpty()) {
            remoteDataSource.getCoffeeList().getOrNull()?.forEach {
                localDataSource.insert(it.toModel())
            }
            emit(runCatching { localDataSource.getCoffeeList() })
        } else {
            emit(runCatching { localDataSource.getCoffeeList() })
            remoteDataSource.getCoffeeList().getOrNull()?.forEach {
                localDataSource.insert(it.toModel())
            }
            emit(runCatching { localDataSource.getCoffeeList() })
        }
    }

    override suspend fun updateCoffeeList(): Result<List<CoffeeEntity>> {
        return runCatching { localDataSource.getCoffeeList() }
    }

}