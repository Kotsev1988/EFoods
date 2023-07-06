package com.example.data.room.kitchen.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.kitchen.entity.RoomKitchen

@Dao
interface KitchenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kitchens: RoomKitchen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg kitchens: RoomKitchen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kitchens: List<RoomKitchen>)

    @Update
    suspend fun update(kitchens: RoomKitchen)

    @Update
    suspend fun update(vararg kitchens: RoomKitchen)

    @Update
    suspend fun update(kitchens: List<RoomKitchen>)

    @Delete
    suspend fun delete(kitchens: RoomKitchen)

    @Delete
    suspend fun delete(vararg kitchens: RoomKitchen)

    @Delete
    suspend fun delete(kitchens: List<RoomKitchen>)

    @Query("SELECT * FROM RoomKitchen")
    fun getAll(): List<RoomKitchen>


}