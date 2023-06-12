package com.example.core.data

import com.example.core.data.api.IFoodAPI
import com.example.core.domain.network.INetworkStates
import com.example.core.data.room.cache.IDishesCache
import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.repository.IGetDishes
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