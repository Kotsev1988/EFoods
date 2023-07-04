package com.example.efoods.di.modules

import android.content.Context
import com.example.core.network.AndroidNetworkStatus
import com.example.core.network.INetworkStates
import com.example.efoods.di.scopes.MainActivityScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class ApiModule {

    @Named("baseURL")
    @Provides
    fun baseURL(): String = "https://run.mocky.io/"

    @MainActivityScope
    @Provides
    fun api(@Named("baseURL") baseURL: String, gson: Gson): com.example.api.IFoodAPI =
        Retrofit.Builder().baseUrl(baseURL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(com.example.api.IFoodAPI::class.java)

    @MainActivityScope
    @Provides
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @MainActivityScope
    @Provides
    fun networkStatus(context: Context): INetworkStates =
        AndroidNetworkStatus(context)
}