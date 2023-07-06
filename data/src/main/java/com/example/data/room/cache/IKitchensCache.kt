
package com.example.data.room.cache
import com.example.domain.entity.СategoryKitchen

interface IKitchensCache {

    suspend fun insertKitchensToDB(kitchens: List<СategoryKitchen>)
    suspend fun getKitchensFromCache(): List<СategoryKitchen>
}