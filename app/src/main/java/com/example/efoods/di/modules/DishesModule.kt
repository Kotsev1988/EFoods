package com.example.efoods.di.modules

import com.example.data.room.Database
import com.example.data.room.cache.IDishesCache
import com.example.data.room.cache.room.DishesCache
import com.example.efoods.di.scopes.MainActivityScope
import dagger.Module
import dagger.Provides
@Module
class DishesModule {

    @MainActivityScope
    @Provides
    fun dishesCache(database: Database): IDishesCache =
        DishesCache(database)
}