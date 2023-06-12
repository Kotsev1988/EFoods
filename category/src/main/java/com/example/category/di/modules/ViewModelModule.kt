package com.example.category.di.modules

import androidx.lifecycle.ViewModel
import com.example.category.di.scopes.CategoryScope
import com.example.category.presentation.viewmodel.CategoryViewModel
import com.example.core.di.modules.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @CategoryScope
    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: CategoryViewModel): ViewModel
}