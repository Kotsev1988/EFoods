package com.example.mycard.presentation.view

interface IMyCardDishesView : IMyDishesView {
    fun setText(text: String)
    fun setPrice(price: String)
    fun setWeght(weight: String)
    fun loadAvatar(url: String)
    fun setCounter(counter: String)
}