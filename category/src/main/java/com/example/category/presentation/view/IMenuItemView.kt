package com.example.category.presentation.view

interface IMenuItemView: IMenuView {
    fun clickButton()
    fun setText(text: String)
    fun changeColor(pos: Int)
}