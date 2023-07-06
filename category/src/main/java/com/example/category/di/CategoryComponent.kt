package com.example.category.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.IFoodAPI
import com.example.category.di.scopes.CategoryScope
import com.example.category.presentation.adapters.dishes.DishesGridAdapter
import com.example.category.presentation.adapters.menus.CategoryHorizontalAdapter
import com.example.category.presentation.fragments.CategoryFragment
import com.example.category.presentation.fragments.DetailFragment
import com.example.core.network.INetworkStates
import com.example.data.room.cache.IDishesCache
import com.example.domain.repository.IGetDishes
import com.example.domain.repository.IMyCardProducts

import dagger.Component
import kotlin.properties.Delegates

@CategoryScope
@Component(
    dependencies = [CategoryComponent.ArticlesDeps::class]
)

interface CategoryComponent {

    interface ArticlesDeps {

        val newsService: IFoodAPI
        val dishes: IGetDishes
        val myCard: IMyCardProducts
        val networkStatus: INetworkStates
        val productCache: IDishesCache
    }

    fun inject(categoryFragment: CategoryFragment)
    fun inject(detailFragment: DetailFragment)
    fun inject(bestSellersProductAdapter: DishesGridAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)
}

interface ArticlesDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: CategoryComponent.ArticlesDeps
    companion object : ArticlesDepsProvider by ArticlesDepsStore
}

object ArticlesDepsStore : ArticlesDepsProvider {

    override var deps: CategoryComponent.ArticlesDeps by Delegates.notNull()
}

internal class ArticlesComponentViewModel : ViewModel() {

    val newDetailsComponent =  DaggerCategoryComponent.builder().articlesDeps(ArticlesDepsProvider.deps).build()
}