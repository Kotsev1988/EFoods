package com.example.data.room.dishes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.dishes.entity.RoomDishes


@Dao
interface DishesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: RoomDishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dishes: RoomDishes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: List<RoomDishes>)

    @Update
    suspend fun update(dishes: RoomDishes)

    @Update
    suspend fun update(vararg dishes: RoomDishes)

    @Update
    suspend fun update(dishes: List<RoomDishes>)

    @Delete
    suspend fun delete(dishes: RoomDishes)

    @Delete
    suspend fun delete(vararg dishes: RoomDishes)

    @Delete
   suspend fun delete(dishes: List<RoomDishes>)

    @Query("SELECT * FROM RoomDishes")
    fun getAll(): List<RoomDishes>



}