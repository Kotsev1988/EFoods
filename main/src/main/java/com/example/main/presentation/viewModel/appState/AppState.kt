package com.example.main.presentation.viewModel.appState

import com.example.main.presentation.view.list.IMyListKitchen
import com.example.core.domain.entity.СategoryKitchen

sealed class AppState{
    data class OnSuccess(

        val myCardProducts: List<СategoryKitchen>,
        val productsListPresenter: IMyListKitchen,

        ) : AppState()

    data class Error(val error: String) : AppState()
}
