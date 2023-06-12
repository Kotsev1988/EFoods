package com.example.core.data.room.cache

import com.example.core.domain.entity.СategoryKitchen
import io.reactivex.rxjava3.core.Single

interface IKitchensCache {

    fun insertKitchensToDB( kitchens: List<СategoryKitchen>): Single<List<СategoryKitchen>>
    fun getKitchensFromCache(): Single<List<СategoryKitchen>>
}