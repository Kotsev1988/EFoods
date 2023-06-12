package com.example.category.presentation.view.lists

import android.view.View
import com.example.category.presentation.view.IMenuView

interface IMenu<V: IMenuView> {
    var itemClickListener: ((V) -> Unit)?
    var itemClickListenerMenu: ((View) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}