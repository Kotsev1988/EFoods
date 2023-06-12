package com.example.core.data.room.kitchen.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomKitchen (
    @PrimaryKey
    var id: Int,
    var image_url: String,
    var name: String
)