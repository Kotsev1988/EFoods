package com.example.main.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.entity.repository.IGetKitchens
import com.example.main.presentation.view.list.MyKitchen
import com.example.main.presentation.viewModel.appState.AppState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var getKitchens: IGetKitchens,
) : ViewModel() {


    private val myKitchenList = MyKitchen()

    private val _lists: MutableLiveData<AppState> = MutableLiveData()
    val listItem: LiveData<AppState>
        get() {
            return _lists
        }

    private val _clickEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val clickEvent: LiveData<Event<String>>
        get() {
            return _clickEvent
        }

    fun init() {
        myKitchenList.itemClickListener = {
            _clickEvent.value = Event(myKitchenList.categories.get(it.pos).name)
        }

        loadKitchens()
    }

    @SuppressLint("CheckResult")
    private fun loadKitchens() {

        getKitchens.getKitchens().observeOn(AndroidSchedulers.mainThread())
            .subscribe({ kitchens ->
                myKitchenList.categories.clear()
                myKitchenList.categories.addAll(kitchens)
                _lists.value = AppState.OnSuccess(myKitchenList.categories, myKitchenList)

        }, {

                _lists.value = it.message?.let { it1 -> AppState.Error(it1) }

        })
    }
}