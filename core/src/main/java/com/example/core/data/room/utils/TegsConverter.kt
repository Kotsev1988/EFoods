package com.example.core.data.room.utils

import androidx.room.TypeConverter
import java.util.Arrays
import java.util.stream.Collectors


class TegsConverter {
    @TypeConverter
    fun fromTegs( tegs: List<String>): String{
        return tegs.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toTegs(data: String): List<String>? {
        return Arrays.asList(*data.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
    }
}