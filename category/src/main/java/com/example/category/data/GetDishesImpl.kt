package com.example.category.data

import com.example.api.IFoodAPI
import com.example.data.room.cache.IDishesCache
import com.example.domain.entity.Dishe
import com.example.domain.repository.IGetDishes
import com.example.core.network.INetworkStates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetDishesImpl(private val api: IFoodAPI,
                    private val networkStatus: INetworkStates,
                    private val dishesCash: IDishesCache
): IGetDishes {
    override fun getDishes(): Single<List<Dishe>> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {

            api.getDishes()
                .flatMap {

                    dishesCash.insertDishesToDB(it.dishes)

                }?: Single.error<List<Dishe>>(RuntimeException("no dishes "))
                .subscribeOn(Schedulers.io())
        }else{
            dishesCash.getDishesFromCache()
        }.subscribeOn(Schedulers.io())
    }
}