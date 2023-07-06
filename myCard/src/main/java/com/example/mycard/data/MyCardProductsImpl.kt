package com.example.mycard.data

import com.example.data.room.Database
import com.example.domain.entity.Dishe
import com.example.domain.repository.IMyCardProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyCardProductsImpl(private val db: Database): IMyCardProducts {
        override suspend fun getAllMyCard(): Flow<List<Dishe>> {
          val listFromCard = db.myCardDao.getAll().map{
              it.map { roomMyCard ->
                  Dishe(
                      roomMyCard.description,
                      roomMyCard.id,
                      roomMyCard.image_url,
                      roomMyCard.name,
                      roomMyCard.price,
                      roomMyCard.tegs,
                      roomMyCard.weight,
                      roomMyCard.count
                  )
              }

          }
            return listFromCard
        }



    override suspend fun insertProductToMyCard(dishe: Dishe){

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


    }

    override suspend fun isContain(id: Int): Boolean =
        db.myCardDao.isContain(id)


    override suspend fun update(id: String, count: Int)=
        db.myCardDao.updateCount(id, count)


    override suspend fun delete(dishe: Dishe) {
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
    }

    override suspend fun getDishById(id: Int): Dishe    {
        val dishRoom = db.myCardDao.findDishById(id)

       return Dishe(
            dishRoom.description,
            dishRoom.id,
            dishRoom.image_url,
            dishRoom.name,
            dishRoom.price,
            dishRoom.tegs,
            dishRoom.weight,
            0
        )
    }

}