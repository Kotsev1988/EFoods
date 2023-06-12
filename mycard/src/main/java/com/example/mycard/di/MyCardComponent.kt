package com.example.mycard.di

import com.example.core.di.BaseComponent
import com.example.core.di.modules.ViewModelFactoryModule
import com.example.mycard.di.modules.MyCardViewModuleModel
import com.example.mycard.di.scope.MyCardScope
import com.example.mycard.presentation.fragments.MyCardFragment
import com.example.mycard.presentation.adapter.MyCardAdapter
import dagger.Component

@MyCardScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [ViewModelFactoryModule::class, MyCardViewModuleModel::class]
)
interface MyCardComponent {

    fun inject(myCardFragment: MyCardFragment)
    fun inject(myCardAdapter: MyCardAdapter)
}