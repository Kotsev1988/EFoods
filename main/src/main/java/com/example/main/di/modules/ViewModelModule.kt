package com.example.main.di.modules

import androidx.lifecycle.ViewModel
import com.example.core.di.modules.ViewModelKey
import com.example.main.di.scopes.MainScope
import com.example.main.presentation.viewModel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: MainViewModel): ViewModel
}