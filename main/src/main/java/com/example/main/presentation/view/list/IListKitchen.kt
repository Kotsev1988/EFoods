package com.example.main.presentation.view.list

import com.example.main.presentation.view.IMyKitchenView

interface IListKitchen<V: IMyKitchenView> {

    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}