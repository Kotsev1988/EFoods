package com.example.category.di.modules

import androidx.lifecycle.ViewModel
import com.example.category.di.scopes.CategoryScope
import com.example.category.presentation.viewmodel.DetailViewModel
import com.example.core.di.modules.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailViewModelModule { @CategoryScope
@Binds
@IntoMap
@ViewModelKey(DetailViewModel::class)
internal abstract fun bindMyViewModel(viewModel: DetailViewModel): ViewModel
}