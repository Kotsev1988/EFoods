package com.example.efoods

import android.app.Application
import android.content.Context
import com.example.core.di.BaseComponent
import com.example.core.di.BaseComponentProvider
import com.example.core.di.DaggerBaseComponent
import com.example.core.di.modules.AppModule
import com.example.core.utils.InjectUtils
import com.example.efoods.di.AppComponent
import com.example.efoods.di.DaggerAppComponent


class App: Application(), BaseComponentProvider {
    companion object {
        lateinit var instance: App
    }

    private lateinit var baseComponent: BaseComponent

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        baseComponent = DaggerBaseComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        baseComponent.inject(this)

        appComponent = DaggerAppComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(this))
            .appModule(AppModule(this))
            .build()
    }

    fun getAppContext(): Context {
        return instance.applicationContext
    }

    override fun provideBaseComponent(): BaseComponent = baseComponent
}