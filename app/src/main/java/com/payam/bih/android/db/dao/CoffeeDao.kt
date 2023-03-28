package com.payam.bih.android.db.dao

import androidx.room.*
import com.payam.bih.android.db.entity.CoffeeEntity

@Dao
interface CoffeeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coffee: CoffeeEntity)

    @Query("SELECT * FROM coffee")
    suspend fun getCoffeeList(): List<CoffeeEntity>

    @Update
    suspend fun update(coffee: CoffeeEntity)

    @Query("SELECT * FROM coffee WHERE coffeeId = :coffeeId")
    suspend fun getCoffeeById(coffeeId: Int): CoffeeEntity

    @Delete
    suspend fun delete(coffee: CoffeeEntity)

}