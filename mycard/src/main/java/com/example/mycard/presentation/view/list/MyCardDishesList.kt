package com.example.mycard.presentation.view.list

import com.example.core.domain.entity.Dishe
import com.example.mycard.presentation.view.IMyCardDishesView

class MyCardDishesList() : IListMyCardDishesView
{
    override var itemClickListenerIncrease: ((IMyCardDishesView) -> Unit)? = null
    override var itemClickListenerDecrease: ((IMyCardDishesView) -> Unit)? = null
    override var itemClickListenerDelete: ((IMyCardDishesView) -> Unit)? = null

     val myCardDishes : ArrayList<Dishe> = arrayListOf()

    override fun bindView(view: IMyCardDishesView) {
        val dish = myCardDishes[view.pos]

        view.setText(dish.name)
        view.setPrice(dish.price.toString())
        view.loadAvatar(dish.image_url)
        view.setCounter(dish.count.toString())
        view.setWeght(dish.weight.toString())

    }

    override fun getCount(): Int  = myCardDishes.size
}