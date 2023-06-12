package com.example.core.di.modules

import android.content.Context
import com.example.core.data.api.IFoodAPI
import com.example.core.data.network.AndroidNetworkStatus
import com.example.core.domain.network.INetworkStates
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Named("baseURL")
    @Provides
    fun baseURL(): String = "https://run.mocky.io/"

    @Singleton
    @Provides
    fun api(@Named("baseURL") baseURL: String, gson: Gson): IFoodAPI =
        Retrofit.Builder().baseUrl(baseURL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IFoodAPI::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun networkStatus(context: Context): INetworkStates = AndroidNetworkStatus(context)
}