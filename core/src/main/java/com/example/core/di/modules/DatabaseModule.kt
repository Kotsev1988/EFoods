package com.example.core.di.modules

import android.content.Context
import androidx.room.Room
import com.example.core.data.room.Database
import com.example.core.data.room.cache.IDishesCache
import com.example.core.data.room.cache.IKitchensCache
import com.example.core.data.room.cache.room.DishesCache
import com.example.core.data.room.cache.room.KitchensCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(context: Context) =
        Room.databaseBuilder(context, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun kitchensCache(database: Database): IKitchensCache = KitchensCache(database)

    @Singleton
    @Provides
    fun dishesCache(database: Database): IDishesCache = DishesCache(database)

}