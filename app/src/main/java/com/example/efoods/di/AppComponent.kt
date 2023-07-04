package com.example.efoods.di

import com.example.api.IFoodAPI
import com.example.category.di.CategoryComponent
import com.example.domain.repository.IGetDishes
import com.example.domain.repository.IMyCardProducts
import com.example.efoods.di.modules.ApiModule
import com.example.efoods.di.modules.AppModule
import com.example.efoods.di.modules.DataModule
import com.example.efoods.di.modules.DatabaseModule
import com.example.efoods.di.scopes.MainActivityScope
import com.example.efoods.presentation.activity.MainActivity
import com.example.mycard.di.MyCardComponent
import dagger.Component

@MainActivityScope
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class,
        DataModule::class
    ]
)
interface AppComponent : CategoryComponent.ArticlesDeps, MyCardComponent.MyCardDeps {

    override val newsService: IFoodAPI

    override val dishes: IGetDishes

    override val myCard: IMyCardProducts


    fun inject(mainActivity: MainActivity)

}