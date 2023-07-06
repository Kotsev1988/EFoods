package com.example.efoods.di.modules

import com.example.api.IFoodAPI
import com.example.category.data.GetDishesImpl
import com.example.category.data.GetKitchensImpl
import com.example.data.room.Database
import com.example.domain.repository.IGetDishes
import com.example.domain.repository.IGetKitchens
import com.example.domain.repository.IMyCardProducts
import com.example.efoods.di.scopes.MainActivityScope
import com.example.mycard.data.MyCardProductsImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @MainActivityScope
    @Provides
    fun getKitchens(api: IFoodAPI
    ): IGetKitchens = GetKitchensImpl(api)

    @MainActivityScope
    @Provides
    fun getDishes(api: IFoodAPI
    ): IGetDishes = GetDishesImpl(api)

    @MainActivityScope
    @Provides
    fun myCardProduct(
        db: Database
    ): IMyCardProducts = MyCardProductsImpl(db)

}