package com.payam.bih.android.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.payam.bih.android.common.GsonUtils.toListByGson
import com.payam.bih.android.common.GsonUtils.toStringByGson

@Entity(
    tableName = "coffee"
)
data class CoffeeEntity(
    @PrimaryKey
    var coffeeId: Int,
    @ColumnInfo(name = "coffeeTitle")
    var coffeeTitle: String,
    @ColumnInfo(name = "coffeeDescription")
    var coffeeDescription: String,
    @ColumnInfo(name = "coffeeImageLink")
    var coffeeImageLink: String,
    @ColumnInfo(name = "ingredients")
    var ingredients: List<String>,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)

class IngredientsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.toListByGson()
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.toStringByGson()
    }
}