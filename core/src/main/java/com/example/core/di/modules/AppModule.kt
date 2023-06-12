package com.example.core.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var app: Application)  {
    @Provides
    @Singleton
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext
}