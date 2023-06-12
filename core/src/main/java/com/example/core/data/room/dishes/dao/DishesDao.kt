package com.example.core.data.room.dishes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.room.dishes.entity.RoomDishes

@Dao
interface DishesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishes: RoomDishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg dishes: RoomDishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishes: List<RoomDishes>)

    @Update
    fun update(dishes: RoomDishes)

    @Update
    fun update(vararg dishes: RoomDishes)

    @Update
    fun update(dishes: List<RoomDishes>)

    @Delete
    fun delete(dishes: RoomDishes)

    @Delete
    fun delete(vararg dishes: RoomDishes)

    @Delete
    fun delete(dishes: List<RoomDishes>)

    @Query("SELECT * FROM RoomDishes")
    fun getAll(): List<RoomDishes>



}