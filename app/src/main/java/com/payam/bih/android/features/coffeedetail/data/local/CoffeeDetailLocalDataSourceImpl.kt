package com.payam.bih.android.features.coffeedetail.data.local

import com.payam.bih.android.db.dao.CoffeeDao
import com.payam.bih.android.db.entity.CoffeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoffeeDetailLocalDataSourceImpl @Inject constructor(
    private val coffeeDao: CoffeeDao
) : CoffeeDetailLocalDataSource {
    override suspend fun update(coffeeEntity: CoffeeEntity): CoffeeEntity {
        return withContext(Dispatchers.IO) {
            coffeeDao.update(coffee = coffeeEntity)
            coffeeDao.getCoffeeById(coffeeId = coffeeEntity.coffeeId)
        }
    }

    override suspend fun getCoffeeById(coffeeId: Int): CoffeeEntity {
        return withContext(Dispatchers.IO) { coffeeDao.getCoffeeById(coffeeId = coffeeId) }
    }
}