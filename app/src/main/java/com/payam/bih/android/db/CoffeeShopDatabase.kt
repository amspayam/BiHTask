package com.payam.bih.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.payam.bih.android.db.dao.CoffeeDao
import com.payam.bih.android.db.entity.CoffeeEntity
import com.payam.bih.android.db.entity.IngredientsTypeConverter


@Database(
    entities = [
        CoffeeEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(
    IngredientsTypeConverter::class
)
abstract class CoffeeShopDatabase : RoomDatabase() {

    abstract val coffeeDao: CoffeeDao

    companion object {
        const val DATABASE_NAME = "coffee_shop_db"
    }

}