package com.example.core.data

import com.example.core.data.api.IFoodAPI
import com.example.core.domain.network.INetworkStates
import com.example.core.data.room.cache.IKitchensCache
import com.example.core.domain.entity.repository.IGetKitchens
import com.example.core.domain.entity.СategoryKitchen
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