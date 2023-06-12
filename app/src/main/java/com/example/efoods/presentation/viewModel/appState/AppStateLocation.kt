package com.example.efoods.presentation.viewModel.appState

sealed class AppStateLocation{

    data class Success(val cities: String) : AppStateLocation()
    data class Error(val error: Throwable) : AppStateLocation()
    data class EmptyData(val message: String) : AppStateLocation()
    data class ShowRationalDialog(val show: String) : AppStateLocation()
}
