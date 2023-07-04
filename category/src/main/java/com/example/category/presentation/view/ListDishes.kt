package com.example.category.presentation.view

import com.example.category.presentation.view.lists.IDishesList
import com.example.domain.entity.Dishe

class ListDishes(): IDishesList {
    override var itemClickListener: ((IDishItemView) -> Unit)? = null

    var dishes: ArrayList<Dishe> = arrayListOf()

    override fun bindView(view: IDishItemView) {
        val dish = dishes[view.pos]

        view.setText(dish.name)

        dish.image_url?.let {
           view.setImage(dish.image_url)
       }

    }

    override fun getCount(): Int = dishes.size
}