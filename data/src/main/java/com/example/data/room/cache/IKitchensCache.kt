
package com.example.data.room.cache
import com.example.domain.entity.СategoryKitchen
import io.reactivex.rxjava3.core.Single

interface IKitchensCache {

    fun insertKitchensToDB( kitchens: List<СategoryKitchen>): Single<List<СategoryKitchen>>
    fun getKitchensFromCache(): Single<List<СategoryKitchen>>
}