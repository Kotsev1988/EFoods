package com.example.main.di

import com.example.core.di.BaseComponent
import com.example.core.di.modules.ViewModelFactoryModule
import com.example.main.di.modules.MainModule
import com.example.main.di.modules.ViewModelModule
import com.example.main.di.scopes.MainScope
import com.example.main.presentation.adapter.MainAdapter
import com.example.main.presentation.fragments.MainFragment
import dagger.Component
@MainScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [MainModule::class,  ViewModelFactoryModule::class, ViewModelModule::class]
)
interface MainComponent {

    @Component.Factory
    interface Factory{
        fun create (baseComponent: BaseComponent): MainComponent
    }

    fun inject(mainFragment: MainFragment)
    fun inject(mainAdapter: MainAdapter)
}