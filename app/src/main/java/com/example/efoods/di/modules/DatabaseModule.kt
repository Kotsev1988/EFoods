package com.example.efoods.di.modules

import android.content.Context
import androidx.room.Room
import com.example.core.data.room.cache.room.KitchensCache
import com.example.data.room.Database
import com.example.data.room.cache.IDishesCache
import com.example.data.room.cache.IKitchensCache
import com.example.data.room.cache.room.DishesCache
import com.example.efoods.di.scopes.MainActivityScope

import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @MainActivityScope
    @Provides
    fun database(context: Context) =
        Room.databaseBuilder(context, Database::class.java, Database.DB_NAME).build()

    @MainActivityScope
    @Provides
    fun kitchensCache(database: Database): IKitchensCache = KitchensCache(database)

    @MainActivityScope
    @Provides
    fun dishesCache(database: Database): IDishesCache =
        DishesCache(database)

}