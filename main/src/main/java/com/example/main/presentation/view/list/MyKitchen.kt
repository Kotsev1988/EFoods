package com.example.main.presentation.view.list

import com.example.main.presentation.view.IMyKitchenView
import com.example.core.domain.entity.СategoryKitchen

class MyKitchen() : IMyListKitchen {
    override var itemClickListener: ((IMyKitchenView) -> Unit)? = null

    val categories : ArrayList<СategoryKitchen> = arrayListOf()

    override fun bindView(view: IMyKitchenView) {
        val category = categories[view.pos]
        view.setText(category.name)
        view.setImage(category.image_url)
    }

    override fun getCount(): Int = categories.size


}