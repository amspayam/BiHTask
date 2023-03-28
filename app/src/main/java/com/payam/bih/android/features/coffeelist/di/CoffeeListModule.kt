package com.payam.bih.android.features.coffeelist.di

import com.payam.bih.android.db.CoffeeShopDatabase
import com.payam.bih.android.di.network.NormalRetrofitClient
import com.payam.bih.android.features.coffeelist.data.CoffeeListRepositoryImpl
import com.payam.bih.android.features.coffeelist.data.api.CoffeeListApiService
import com.payam.bih.android.features.coffeelist.data.local.CoffeeListLocalDataSource
import com.payam.bih.android.features.coffeelist.data.local.CoffeeListLocalDataSourceImpl
import com.payam.bih.android.features.coffeelist.data.network.CoffeeListRemoteDataSource
import com.payam.bih.android.features.coffeelist.data.network.CoffeeListRemoteDataSourceImpl
import com.payam.bih.android.features.coffeelist.domain.repository.CoffeeListRepository
import com.payam.bih.android.features.coffeelist.domain.usecase.GetCoffeeListUseCase
import com.payam.bih.android.features.coffeelist.domain.usecase.UpdateCoffeeListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoffeeListModule {

    @Singleton
    @Provides
    fun provideCoffeeListApi(
        @NormalRetrofitClient retrofit: Retrofit
    ): CoffeeListApiService {
        return retrofit.create(CoffeeListApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoffeeListRemoteDataSource(api: CoffeeListApiService)
            : CoffeeListRemoteDataSource {
        return CoffeeListRemoteDataSourceImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideCoffeeListLocalDataSource(db: CoffeeShopDatabase)
            : CoffeeListLocalDataSource {
        return CoffeeListLocalDataSourceImpl(coffeeDao = db.coffeeDao)
    }

    @Provides
    @Singleton
    fun provideCoffeeListRepository(
        localDataSource: CoffeeListLocalDataSource,
        remoteDataSource: CoffeeListRemoteDataSource
    ): CoffeeListRepository {
        return CoffeeListRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCoffeeListUseCase(
        coffeeListRepository: CoffeeListRepository
    ): GetCoffeeListUseCase {
        return GetCoffeeListUseCase(
            coffeeListRepository = coffeeListRepository
        )
    }

    @Provides
    @Singleton
    fun provideUpdateCoffeeListUseCase(
        coffeeListRepository: CoffeeListRepository
    ): UpdateCoffeeListUseCase {
        return UpdateCoffeeListUseCase(
            repository = coffeeListRepository
        )
    }

}