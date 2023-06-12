package com.example.category.presentation.view.lists

import com.example.category.presentation.view.IDishView

interface IDishes<V: IDishView>  {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}