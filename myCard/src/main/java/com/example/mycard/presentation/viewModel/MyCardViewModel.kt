package com.example.mycard.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Dishe
import com.example.domain.repository.IMyCardProducts
import com.example.mycard.presentation.view.list.MyCardDishesList
import com.example.mycard.presentation.viewModel.appState.AppStateMyCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class MyCardViewModel(
    private val myCard: IMyCardProducts,
) : ViewModel() {

    private val _lists: MutableLiveData<AppStateMyCard> = MutableLiveData()
    val listItem: LiveData<AppStateMyCard>
        get() {
            return _lists
        }

    private val myCardDishes = MyCardDishesList()
    private val prices: HashMap<Int, Int> = hashMapOf()

    @SuppressLint("CheckResult")
    fun init() {

        myCardDishes.itemClickListenerIncrease = {
            val key = myCardDishes.myCardDishes[it.pos].id
            val defaultValue = myCardDishes.myCardDishes[it.pos].count

            prices[key] = prices.getOrDefault(key, defaultValue) + 1

            prices[key]?.let { it1 ->

                viewModelScope.launch {
                    myCard.update(key.toString(), it1)
                }
            }
        }

        myCardDishes.itemClickListenerDecrease = {
            val key = myCardDishes.myCardDishes[it.pos].id
            val defaultValue = myCardDishes.myCardDishes[it.pos].count

            prices[key] = prices.getOrDefault(key, defaultValue) - 1

            prices[key]?.let { it1 ->

                if (it1 == 0) {
                    viewModelScope.launch {
                        myCard.delete(myCardDishes.myCardDishes[it.pos])
                    }
                } else {
                    viewModelScope.launch {
                        myCard.update(key.toString(), it1)
                    }
                }
            }
        }

        getAllDishesFromCard()
    }

    @SuppressLint("CheckResult")
    private fun getAllDishesFromCard() {

        viewModelScope.launch {
            myCard.getAllMyCard().collect { listOfDishes ->

                withContext(Dispatchers.Main) {
                    myCardDishes.myCardDishes.clear()
                    myCardDishes.myCardDishes.addAll(listOfDishes)
                    totalPrice(listOfDishes)
                    _lists.value = AppStateMyCard.OnSuccess(myCardDishes.myCardDishes, myCardDishes)
                }
            }
        }
    }

    private fun totalPrice(myProducts: List<Dishe>) {
        var totalPrice = 0
        myProducts.forEach {
            totalPrice += it.price * it.count
        }
        _lists.value = AppStateMyCard.TotalPrice(totalPrice.toString())
    }

    class Factory @Inject constructor(
        private val myCard: Provider<IMyCardProducts>,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MyCardViewModel::class.java)
            return MyCardViewModel(myCard.get()) as T
        }
    }
}