package com.example.mycard.presentation.view.list

import com.example.mycard.presentation.view.IMyCardDishesView

interface IListMyDishes<V: IMyCardDishesView> {

    var itemClickListenerIncrease: ((V) -> Unit)?
    var itemClickListenerDecrease: ((V) -> Unit)?
    var itemClickListenerDelete: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}