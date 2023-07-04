package com.example.mycard.data

import com.example.data.room.Database
import com.example.domain.entity.Dishe
import com.example.domain.repository.IMyCardProducts
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MyCardProductsImpl(private val db: Database): IMyCardProducts {
    override fun getAllMyCard(): Single<List<Dishe>> = Single.fromCallable {
        db.myCardDao.getAll().map {
            Dishe(
                it.description,
                it.id,
                it.image_url,
                it.name,
                it.price,
                it.tegs,
                it.weight,
                it.count
            )
        }
    }.subscribeOn(Schedulers.io())

    override fun insertProductToMyCard(dishe: Dishe): Single<Dishe> = Single.fromCallable {

        val dishesRoom = com.example.data.room.mycard.entity.RoomMyCard(
            dishe.description,
            dishe.id,
            dishe.image_url,
            dishe.name,
            dishe.price,
            dishe.tegs,
            dishe.weight,
            1
        )

        db.myCardDao.insert(dishesRoom)
        dishe
    }.subscribeOn(Schedulers.io())


    override fun isContain(id: Int): Single<Boolean> = Single.fromCallable {
        db.myCardDao.isContain(id)
    }.subscribeOn(Schedulers.io())

    override fun update(id: String, count: Int): Completable = Completable.fromCallable {
        db.myCardDao.updateCount(id, count)
    }.subscribeOn(Schedulers.io())

    override fun delete(dishe: Dishe): Completable  = Completable.fromCallable {
        val roomMyCard = com.example.data.room.mycard.entity.RoomMyCard(
            dishe.description,
            dishe.id,
            dishe.image_url,
            dishe.name,
            dishe.price,
            dishe.tegs,
            dishe.weight,
            dishe.count
        )
        db.myCardDao.delete(roomMyCard)
    }.subscribeOn(Schedulers.io())

    override fun getDishById(id: Int): Single<Dishe>  = Single.fromCallable {
        val dishRoom = db.myCardDao.findDishById(id)

        Dishe(
            dishRoom.description,
            dishRoom.id,
            dishRoom.image_url,
            dishRoom.name,
            dishRoom.price,
            dishRoom.tegs,
            dishRoom.weight,
            0
        )
    }.subscribeOn(Schedulers.io())


}