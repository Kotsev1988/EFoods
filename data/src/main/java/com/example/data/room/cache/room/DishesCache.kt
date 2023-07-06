package com.example.data.room.cache.room

import com.example.data.room.Database
import com.example.data.room.cache.IDishesCache
import com.example.data.room.dishes.entity.RoomDishes
import com.example.domain.entity.Dishe

class DishesCache(private val db: Database) : IDishesCache {
    override suspend fun insertDishesToDB(kitchens: List<Dishe>) {

    val roomDishes = kitchens.map { dishes ->
        val imageUrl = if (dishes.image_url == null){
            "image"
        }else{
            dishes.image_url
        }
        RoomDishes(
            description = dishes.description,
            id = dishes.id,
            image_url = imageUrl,
            name = dishes.name,
            price = dishes.price,
            tegs = dishes.tegs,
            weight = dishes.weight
        )
    }
        db.dishesDao.insert(roomDishes)
}

    override suspend fun getDishesFromCache(): List<Dishe>  {
        val dishes: ArrayList<Dishe> = arrayListOf()
        val dishesFromDB = db.dishesDao.getAll()
       dishesFromDB.map {
              dishes.add(Dishe(it.description, it.id, it.image_url, it.name, it.price, it.tegs, it.weight, 0))
          }
        return dishes
    }
}