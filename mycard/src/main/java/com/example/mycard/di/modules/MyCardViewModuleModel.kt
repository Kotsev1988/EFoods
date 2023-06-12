package com.example.mycard.di.modules

import androidx.lifecycle.ViewModel
import com.example.core.di.modules.ViewModelKey
import com.example.mycard.di.scope.MyCardScope
import com.example.mycard.presentation.viewModel.MyCardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
abstract class MyCardViewModuleModel {
    @MyCardScope
    @Binds
    @IntoMap
    @ViewModelKey(MyCardViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: MyCardViewModel): ViewModel
}