package com.example.domain.repository

import com.example.domain.entity.Dishe
import kotlinx.coroutines.flow.Flow

interface IMyCardProducts {
      suspend fun getAllMyCard(): Flow<List<Dishe>>
   suspend fun insertProductToMyCard(productsItem: Dishe)
    suspend fun isContain(id: Int): Boolean
    suspend fun update(id: String, count: Int)
    suspend fun delete(id: Dishe)
    suspend fun getDishById(id: Int): Dishe
}