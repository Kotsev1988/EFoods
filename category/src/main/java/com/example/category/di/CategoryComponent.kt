package com.example.category.di

import com.example.category.di.modules.DetailViewModelModule
import com.example.category.di.modules.ViewModelModule
import com.example.category.di.scopes.CategoryScope
import com.example.category.presentation.adapters.dishes.DishesGridAdapter
import com.example.category.presentation.adapters.menus.CategoryHorizontalAdapter
import com.example.category.presentation.fragments.CategoryFragment
import com.example.category.presentation.fragments.DetailFragment
import com.example.core.di.BaseComponent
import com.example.core.di.modules.ViewModelFactoryModule
import dagger.Component

@CategoryScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [ViewModelFactoryModule::class, ViewModelModule::class, DetailViewModelModule::class]
)

interface CategoryComponent {

    @Component.Factory
    interface Factory{
        fun create (baseComponent: BaseComponent): CategoryComponent
    }

    fun inject(categoryFragment: CategoryFragment)
    fun inject(detailFragment: DetailFragment)


    fun inject(bestSellersProductAdapter: DishesGridAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)
}