package com.example.main.presentation.viewModel

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false

    fun goToMenuDetailsIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

}