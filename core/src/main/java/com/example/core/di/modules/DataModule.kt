package com.example.core.di.modules

import com.example.core.data.GetDishesImpl
import com.example.core.data.GetKitchensImpl
import com.example.core.data.MyCardProductsImpl
import com.example.core.data.api.IFoodAPI
import com.example.core.data.room.Database
import com.example.core.domain.network.INetworkStates
import com.example.core.data.room.cache.IDishesCache
import com.example.core.data.room.cache.IKitchensCache
import com.example.core.domain.entity.repository.IGetDishes
import com.example.core.domain.entity.repository.IGetKitchens
import com.example.core.domain.myCard.IMyCardProducts
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun getKitchens(api: IFoodAPI,
                    networkStatus: INetworkStates,
                    kitchensCash: IKitchensCache
    ):IGetKitchens = GetKitchensImpl(api, networkStatus, kitchensCash)

    @Singleton
    @Provides
    fun getDishes(api: IFoodAPI,
                  networkStatus: INetworkStates,
                  dishesCache: IDishesCache
    ):IGetDishes = GetDishesImpl(api, networkStatus, dishesCache)

    @Singleton
    @Provides
    fun myCardProduct(
        db: Database
    ): IMyCardProducts = MyCardProductsImpl(db)

}