package com.payam.bih.android.di.network

import com.payam.bih.android.BuildConfig
import com.payam.bih.android.common.GsonUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class IsDebugBuild

@Qualifier
annotation class LoggerInterceptorProvider

@Qualifier
annotation class NormalOkHttpClient

@Qualifier
annotation class NormalRetrofitClient

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @IsDebugBuild
    @Singleton
    @Provides
    fun provideIsDebugBuild(): Boolean = BuildConfig.DEBUG

    @LoggerInterceptorProvider
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @NormalOkHttpClient
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(
        @LoggerInterceptorProvider loggerInterceptor: Interceptor
    ): OkHttpClient = clientBuilder(
        loggerInterceptor = loggerInterceptor
    )

    @NormalRetrofitClient
    @Singleton
    @Provides
    fun provideNormalRetrofitClient(
        @NormalOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit = retrofitBuilder(okHttpClient)

    private fun retrofitBuilder(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(GsonUtils.gson)
        )
        .baseUrl("https://api.sampleapis.com/")
        .build()

    private fun clientBuilder(
        loggerInterceptor: Interceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggerInterceptor)

            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
        }
        return client.build()
    }
}
