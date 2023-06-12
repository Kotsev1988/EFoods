package com.example.core.di

import android.app.Application
import android.widget.ImageView
import com.example.core.data.api.IFoodAPI
import com.example.core.domain.image.ILoadImage
import com.example.core.di.modules.ApiModule
import com.example.core.di.modules.AppModule
import com.example.core.di.modules.DataModule
import com.example.core.di.modules.DatabaseModule
import com.example.core.di.modules.LoadImageModule
import com.example.core.domain.entity.repository.IGetDishes
import com.example.core.domain.entity.repository.IGetKitchens
import com.example.core.domain.myCard.IMyCardProducts
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class,
        DataModule::class,
        LoadImageModule::class

    ])
interface BaseComponent {

    fun inject(app: Application)
    fun api(): IFoodAPI
    fun gson(): Gson
    fun getKitchens(): IGetKitchens
    fun getDishes(): IGetDishes
    fun loadImage(): ILoadImage<ImageView>
    fun myCardProduct(
    ): IMyCardProducts

    
}