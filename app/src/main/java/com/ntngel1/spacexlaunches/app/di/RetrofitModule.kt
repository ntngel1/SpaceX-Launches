package com.ntngel1.spacexlaunches.app.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ntngel1.spacexlaunches.BuildConfig
import com.ntngel1.spacexlaunches.gateway.retrofit.SpaceXApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.aaronhe.threetengson.ThreeTenGsonAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideSpaceXApi(retrofit: Retrofit): SpaceXApi =
        retrofit.create(SpaceXApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: Converter.Factory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .let(ThreeTenGsonAdapter::registerZonedDateTime)
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}