package com.example.core.data.api

import com.example.core.domain.entity.Categories
import com.example.core.domain.entity.Menus
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IFoodAPI {

    @GET("v3/058729bd-1402-4578-88de-265481fd7d54")
    fun getKitchens(): Single<Categories>

    @GET("v3/c7a508f2-a904-498a-8539-09d96785446e")
    fun getDishes(): Single<Menus>
}