package com.example.efoods.di

import com.example.core.di.BaseComponent
import com.example.core.di.modules.AppModule
import com.example.efoods.di.scopes.MainActivityScope
import com.example.efoods.presentation.activity.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}