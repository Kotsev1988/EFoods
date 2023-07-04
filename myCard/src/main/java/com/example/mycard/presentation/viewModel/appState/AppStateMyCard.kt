package com.example.mycard.presentation.viewModel.appState

import com.example.domain.entity.Dishe
import com.example.mycard.presentation.view.list.IListMyCardDishesView

sealed class AppStateMyCard{
    data class OnSuccess(

        val myCardProducts: List<Dishe>,
        val myCardList: IListMyCardDishesView,

        ) : AppStateMyCard()

    data class OnUpdate(

        val myCardList: IListMyCardDishesView,

        ) : AppStateMyCard()

    data class TotalPrice(

        val totalPrice: String,

        ) : AppStateMyCard()

    data class Error(val error: String) : AppStateMyCard()
}