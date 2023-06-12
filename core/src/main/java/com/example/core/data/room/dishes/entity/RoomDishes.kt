package com.example.core.data.room.dishes.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.core.data.room.utils.TegsConverter

@Entity
data class RoomDishes (
    var description: String,
    @PrimaryKey
    var id: Int,
    var image_url: String,
    var name: String,
    var price: Int,
    @TypeConverters(TegsConverter::class)
    var tegs: List<String>,
    var weight: Int
)