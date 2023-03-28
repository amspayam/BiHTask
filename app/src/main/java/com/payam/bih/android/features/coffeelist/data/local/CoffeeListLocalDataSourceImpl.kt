package com.payam.bih.android.features.coffeelist.data.local

import com.payam.bih.android.db.dao.CoffeeDao
import com.payam.bih.android.db.entity.CoffeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoffeeListLocalDataSourceImpl @Inject constructor(
    private val coffeeDao: CoffeeDao
) : CoffeeListLocalDataSource {

    override suspend fun insert(coffeeEntity: CoffeeEntity) {
        return withContext(Dispatchers.IO) { coffeeDao.insert(coffee = coffeeEntity) }
    }

    /**
     * Using pagination would be a better approach for this method.
     * However, due to the time constraint and the unchanging nature of the API,
     * the current approach was preferred.
     * */
    override suspend fun getCoffeeList(): List<CoffeeEntity> {
        return withContext(Dispatchers.IO) { coffeeDao.getCoffeeList() }
    }

}