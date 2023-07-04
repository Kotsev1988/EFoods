package com.example.efoods.di.modules

import com.example.api.IFoodAPI
import com.example.category.data.GetDishesImpl
import com.example.category.data.GetKitchensImpl
import com.example.mycard.data.MyCardProductsImpl
import com.example.data.room.Database
import com.example.data.room.cache.IDishesCache
import com.example.data.room.cache.IKitchensCache
import com.example.domain.repository.IGetDishes
import com.example.domain.repository.IGetKitchens
import com.example.domain.repository.IMyCardProducts
import com.example.core.network.INetworkStates
import com.example.efoods.di.scopes.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @MainActivityScope
    @Provides
    fun getKitchens(api: IFoodAPI,
                    networkStatus: INetworkStates,
                    kitchensCash: IKitchensCache
    ): IGetKitchens = GetKitchensImpl(api, networkStatus, kitchensCash)

    @MainActivityScope
    @Provides
    fun getDishes(api: IFoodAPI,
                  networkStatus: INetworkStates,
                  dishesCache: IDishesCache
    ): IGetDishes = GetDishesImpl(api, networkStatus, dishesCache)

    @MainActivityScope
    @Provides
    fun myCardProduct(
        db: Database
    ): IMyCardProducts = MyCardProductsImpl(db)

}