package com.example.category.presentation.viewmodel.appState

import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.category.presentation.view.lists.IDishesList
import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.MenuCategory

sealed class DetailAppState{

    data class OnSuccess(
        val dishe: Dishe
    ) : DetailAppState()

    data class IsContain(
        val isContain: Boolean
    ) : DetailAppState()

    data class AddToCard(
        val isAdded: Boolean
    ) : DetailAppState()

    data class Error(val error: String) : DetailAppState()
}
