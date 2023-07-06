package com.example.data.room.cache

import com.example.domain.entity.Dishe

interface IDishesCache {
   suspend fun insertDishesToDB(kitchens: List<Dishe>)
   suspend fun getDishesFromCache(): List<Dishe>

}