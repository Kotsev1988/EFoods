package com.example.core.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.core.viewModelFactory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}