package com.example.data.room.cache.room

import com.example.data.room.Database
import com.example.data.room.cache.IKitchensCache
import com.example.data.room.kitchen.entity.RoomKitchen
import com.example.domain.entity.СategoryKitchen

class KitchensCache(private val db: Database): IKitchensCache {

    override suspend fun insertKitchensToDB(kitchens: List<СategoryKitchen>)  {
            val roomKitchens = kitchens.map {kitchen ->
                RoomKitchen(id = kitchen.id, image_url = kitchen.image_url, name = kitchen.name)
            }
            db.kitchenDao.insert(roomKitchens)
        }

    override suspend fun getKitchensFromCache(): List<СategoryKitchen> {
        val kitchen : ArrayList<СategoryKitchen> = arrayListOf()
        val kitchensFromDB = db.kitchenDao.getAll()
        kitchensFromDB.map {
            kitchen.add(СategoryKitchen(it.id, it.image_url, it.name))
        }
       return kitchen
    }
}