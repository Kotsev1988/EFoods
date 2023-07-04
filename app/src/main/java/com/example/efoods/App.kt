package com.example.efoods

import android.app.Application
import android.content.Context
import com.example.category.di.ArticlesDepsStore
import com.example.efoods.di.AppComponent
import com.example.efoods.di.DaggerAppComponent
import com.example.efoods.di.modules.AppModule
import com.example.mycard.di.ArticlesMyCard


class App: Application(){
    companion object {
        lateinit var instance: App
    }


    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        ArticlesDepsStore.deps = appComponent
        ArticlesMyCard.myCard = appComponent
    }

    fun getAppContext(): Context {
        return instance.applicationContext
    }

}