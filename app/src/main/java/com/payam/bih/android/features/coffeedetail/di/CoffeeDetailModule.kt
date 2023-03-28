package com.payam.bih.android.features.coffeedetail.di

import com.payam.bih.android.db.CoffeeShopDatabase
import com.payam.bih.android.di.network.NormalRetrofitClient
import com.payam.bih.android.features.coffeedetail.data.CoffeeDetailRepositoryImpl
import com.payam.bih.android.features.coffeedetail.data.api.CoffeeDetailApiService
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSource
import com.payam.bih.android.features.coffeedetail.data.local.CoffeeDetailLocalDataSourceImpl
import com.payam.bih.android.features.coffeedetail.data.network.CoffeeDetailRemoteDataSource
import com.payam.bih.android.features.coffeedetail.data.network.CoffeeDetailRemoteDataSourceImpl
import com.payam.bih.android.features.coffeedetail.domain.repository.CoffeeDetailRepository
import com.payam.bih.android.features.coffeedetail.domain.usecase.CoffeeReviewUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.FavoriteCoffeeUseCase
import com.payam.bih.android.features.coffeedetail.domain.usecase.GetCoffeeDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoffeeDetailModule {

    @Singleton
    @Provides
    fun provideCoffeeDetailApi(
        @NormalRetrofitClient retrofit: Retrofit
    ): CoffeeDetailApiService {
        return retrofit.create(CoffeeDetailApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoffeeDetailRemoteDataSource(api: CoffeeDetailApiService)
            : CoffeeDetailRemoteDataSource {
        return CoffeeDetailRemoteDataSourceImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideCoffeeDetailLocalDataSource(db: CoffeeShopDatabase)
            : CoffeeDetailLocalDataSource {
        return CoffeeDetailLocalDataSourceImpl(coffeeDao = db.coffeeDao)
    }

    @Provides
    @Singleton
    fun provideCoffeeDetailRepository(
        localDataSource: CoffeeDetailLocalDataSource,
        remoteDataSource: CoffeeDetailRemoteDataSource
    ): CoffeeDetailRepository {
        return CoffeeDetailRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGetCoffeeDetailUseCase(
        coffeeDetailRepository: CoffeeDetailRepository
    ): GetCoffeeDetailUseCase {
        return GetCoffeeDetailUseCase(
            coffeeDetailRepository = coffeeDetailRepository
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteCoffeeUseCase(
        coffeeDetailRepository: CoffeeDetailRepository
    ): FavoriteCoffeeUseCase {
        return FavoriteCoffeeUseCase(
            coffeeDetailRepository = coffeeDetailRepository
        )
    }

    @Provides
    @Singleton
    fun provideCoffeeReviewUseCase(
        coffeeDetailRepository: CoffeeDetailRepository
    ): CoffeeReviewUseCase {
        return CoffeeReviewUseCase(
            repository = coffeeDetailRepository
        )
    }

}