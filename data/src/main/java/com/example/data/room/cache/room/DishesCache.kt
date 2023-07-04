package com.example.data.room.cache.room

import com.example.domain.entity.Dishe
import com.example.data.room.Database
import com.example.data.room.cache.IDishesCache
import com.example.data.room.dishes.entity.RoomDishes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DishesCache(private val db: Database) : IDishesCache {

    override fun insertDishesToDB(dishes: List<Dishe>): Single<List<Dishe>> =
        Single.fromCallable {
            val roomDishes = dishes.map { dishe ->
                RoomDishes(
                    description = dishe.description,
                    id = dishe.id,
                    image_url = "image",
                    name = dishe.name,
                    price = dishe.price,
                    tegs = dishe.tegs,
                    weight = dishe.weight
                )
            }
            db.dishesDao.insert(roomDishes)
            dishes
        }.subscribeOn(Schedulers.io())

    override fun getDishesFromCache(): Single<List<Dishe>> = Single.fromCallable {
        val dishes: ArrayList<Dishe> = arrayListOf()
        val dishesFromDB = db.dishesDao.getAll()
        dishesFromDB.map {
            dishes.add(Dishe(it.description, it.id, it.image_url, it.name, it.price, it.tegs, it.weight, 0))
        }
        dishes
    }


}