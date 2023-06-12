package com.example.category.presentation.viewmodel.appState

import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.category.presentation.view.lists.IDishesList
import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.MenuCategory
import com.example.core.domain.entity.Menus

sealed class CategoryAppState {
    data class OnSuccess(
        val menuCategory: MenuCategory,
        val listMenu: ListMenu,
        val dishes: ArrayList<Dishe>,
        val listDishes: ListDishes
    ) : CategoryAppState()

    data class SetDishes(
        val menuCategory: MenuCategory,
        val listMenu: ListMenu,
        val dishes: ArrayList<Dishe>,
        val listDishes: ListDishes
    ) : CategoryAppState()


    data class Error(val error: String) : CategoryAppState()
    data class ShowDialogFragment(val dish: Dishe) : CategoryAppState()
}