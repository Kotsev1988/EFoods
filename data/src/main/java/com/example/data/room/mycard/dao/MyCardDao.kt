package com.example.core.data.room.myCard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.mycard.entity.RoomMyCard

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishes: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg dishes: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishes: List<RoomMyCard>)

    @Update
    fun update(dishes: RoomMyCard)

    @Update
    fun update(vararg dishes: RoomMyCard)

    @Update
    fun update(dishes: List<RoomMyCard>)

    @Query("UPDATE RoomMyCard SET count=:count WHERE id=:id")
    fun updateCount(id: String, count: Int)

    @Delete
    fun delete(dishes: RoomMyCard)

    @Delete
    fun delete(vararg dishes: RoomMyCard)

    @Delete
    fun delete(dishes: List<RoomMyCard>)

    @Query("SELECT * FROM RoomMyCard")
    fun getAll(): List<RoomMyCard>

    @Query("SELECT EXISTS (SELECT * FROM RoomMyCard WHERE id = :id)")
    fun isContain(id: Int): Boolean

    @Query("SELECT * FROM RoomMyCard WHERE id = :id")
    fun findDishById(id: Int): RoomMyCard



}