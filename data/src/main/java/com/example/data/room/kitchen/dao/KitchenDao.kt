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
    fun insert(kitchens: RoomKitchen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg kitchens: RoomKitchen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kitchens: List<RoomKitchen>)

    @Update
    fun update(kitchens: RoomKitchen)

    @Update
    fun update(vararg kitchens: RoomKitchen)

    @Update
    fun update(kitchens: List<RoomKitchen>)

    @Delete
    fun delete(kitchens: RoomKitchen)

    @Delete
    fun delete(vararg kitchens: RoomKitchen)

    @Delete
    fun delete(kitchens: List<RoomKitchen>)

    @Query("SELECT * FROM RoomKitchen")
    fun getAll(): List<RoomKitchen>


}