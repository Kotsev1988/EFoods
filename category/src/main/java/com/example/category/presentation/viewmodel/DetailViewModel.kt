package com.example.category.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.category.presentation.viewmodel.appState.DetailAppState
import com.example.core.domain.entity.Dishe
import com.example.core.domain.myCard.IMyCardProducts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
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
}