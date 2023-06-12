package com.example.category.presentation.view

import android.view.View
import com.example.category.presentation.view.lists.IListMenu
import com.example.core.domain.entity.MenuCategory

class ListMenu(): IListMenu {
    override var itemClickListenerMenu: ((View) -> Unit)? = null
    override var itemClickListener: ((IMenuItemView) -> Unit)? = null

    val menuCategory = MenuCategory()

    override fun bindView(view: IMenuItemView) {

        var menu = menuCategory[view.pos]

        view.setText(menu.name)
        view.clickButton()

        if (menu.select ){
            view.changeColor(0)
        }else{
            view.changeColor(1)
        }
    }

    override fun getCount(): Int = menuCategory.size
}