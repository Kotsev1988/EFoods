package com.example.category.data

import com.example.api.IFoodAPI
import com.example.data.room.cache.IKitchensCache
import com.example.domain.repository.IGetKitchens
import com.example.domain.entity.СategoryKitchen
import com.example.core.network.INetworkStates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetKitchensImpl(
    private val api: IFoodAPI,
    private val networkStatus: INetworkStates,
    private val kitchensCash: IKitchensCache,
) : IGetKitchens {

    override fun getKitchens(): Single<List<СategoryKitchen>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {


                api.getKitchens().flatMap {
                    kitchensCash.insertKitchensToDB(it.сategories)
                } ?: Single.error<List<СategoryKitchen>>(RuntimeException("no category "))
                    .subscribeOn(Schedulers.io())
            } else {
                kitchensCash.getKitchensFromCache()
            }.subscribeOn(Schedulers.io())
        }
}