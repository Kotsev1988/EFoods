package com.example.category.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.category.presentation.viewmodel.appState.DetailAppState
import com.example.domain.entity.Dishe
import com.example.domain.repository.IMyCardProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class DetailViewModel (
    private val myCard: IMyCardProducts
): ViewModel() {

    private val _lists: MutableLiveData<DetailAppState> = MutableLiveData()
    val listItem: LiveData<DetailAppState>
        get() {
            return _lists
        }

     private lateinit var dishe: Dishe

    fun init(dish: Dishe?){

        if (dish != null) {
            dishe = dish
            _lists.value = DetailAppState.OnSuccess(dish)
            isContain(dish.id)
        }
    }


    @SuppressLint("CheckResult")
    private fun isContain(id: Int) {
        viewModelScope.launch {
            var isContain: Boolean
            withContext(Dispatchers.IO){
                isContain = myCard.isContain(id)
            }

            withContext(Dispatchers.Main){
                _lists.value = DetailAppState.IsContain(isContain)
            }
        }
    }

     fun addToCard() {
         viewModelScope.launch {
             withContext(Dispatchers.IO){
                 myCard.insertProductToMyCard(dishe)
             }

             withContext(Dispatchers.Main){
                 _lists.value = DetailAppState.AddToCard(true)
             }

         }
    }

    class Factory @Inject constructor(
        private val myCard: Provider<IMyCardProducts>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DetailViewModel::class.java)
            return DetailViewModel(myCard.get()) as T
        }
    }
}