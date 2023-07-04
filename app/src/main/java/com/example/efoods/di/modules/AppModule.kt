package com.example.efoods.di.modules

import android.app.Application
import android.content.Context
import com.example.efoods.di.scopes.MainActivityScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(var app: Application)  {
    @Provides
    @MainActivityScope
    fun provideApp(): Application = app

    @Provides
    @MainActivityScope
    fun provideContext(): Context = app.applicationContext
}