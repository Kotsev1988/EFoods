package com.example.category.presentation.viewmodel.appState

import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.domain.entity.Dishe
import com.example.domain.entity.MenuCategory

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