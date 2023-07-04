package com.example.category.presentation.viewmodel.appState

import com.example.domain.entity.Dishe

sealed class DetailAppState{

    data class OnSuccess(
        val dishe: Dishe
    ) : DetailAppState()

    data class IsContain(
        val isContain: Boolean
    ) : DetailAppState()

    data class AddToCard(
        val isAdded: Boolean
    ) : DetailAppState()

    data class Error(val error: String) : DetailAppState()
}
