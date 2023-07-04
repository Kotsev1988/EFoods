package com.example.category.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.category.presentation.viewmodel.appState.DetailAppState
import com.example.domain.entity.Dishe
import com.example.domain.repository.IMyCardProducts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
        myCard.isContain(id).observeOn(AndroidSchedulers.mainThread()).subscribe({
            _lists.value = DetailAppState.IsContain(it)

        },
            {
                _lists.value = it.message?.let { it1 -> DetailAppState.Error(it1) }
            })
    }

     fun addToCard() {
        myCard.let {
            myCard.insertProductToMyCard(dishe).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _lists.value = DetailAppState.AddToCard(true)
                }, {
                    _lists.value = it.message?.let { it1 -> DetailAppState.Error(it1) }
                })
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