package com.payam.bih.android.di

import android.app.Application
import androidx.room.Room
import com.payam.bih.android.db.CoffeeShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Provides
    @Singleton
    fun provideCoffeeDatabase(app: Application): CoffeeShopDatabase {
        return Room.databaseBuilder(
            app,
            CoffeeShopDatabase::class.java,
            CoffeeShopDatabase.DATABASE_NAME
        ).build()
    }
}