package com.example.data.room.cache

import com.example.domain.entity.Dishe
import io.reactivex.rxjava3.core.Single

interface IDishesCache {
    fun insertDishesToDB( kitchens: List<Dishe>): Single<List<Dishe>>
    fun getDishesFromCache(): Single<List<Dishe>>

}