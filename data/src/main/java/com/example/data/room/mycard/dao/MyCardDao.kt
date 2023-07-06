package com.example.data.room.mycard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.room.mycard.entity.RoomMyCard
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg dishes: RoomMyCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishes: List<RoomMyCard>)

    @Update
    suspend fun update(dishes: RoomMyCard)

    @Update
    suspend fun update(vararg dishes: RoomMyCard)

    @Update
    suspend fun update(dishes: List<RoomMyCard>)

    @Query("UPDATE RoomMyCard SET count=:count WHERE id=:id")
    suspend fun updateCount(id: String, count: Int)

    @Delete
    suspend fun delete(dishes: RoomMyCard)

    @Delete
    suspend fun delete(vararg dishes: RoomMyCard)

    @Delete
    suspend fun delete(dishes: List<RoomMyCard>)

    @Query("SELECT * FROM RoomMyCard")
    fun getAll(): Flow<List<RoomMyCard>>

    @Query("SELECT EXISTS (SELECT * FROM RoomMyCard WHERE id = :id)")
    fun isContain(id: Int): Boolean

    @Query("SELECT * FROM RoomMyCard WHERE id = :id")
    fun findDishById(id: Int): RoomMyCard



}