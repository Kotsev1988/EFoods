package com.example.mycard.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.entity.Dishe
import com.example.core.domain.myCard.IMyCardProducts
import com.example.mycard.presentation.view.list.MyCardDishesList
import com.example.mycard.presentation.viewModel.appState.AppStateMyCard
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MyCardViewModel @Inject constructor(
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

                myCard.update(key.toString(), it1).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        myCard.getAllMyCard()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                myCardDishes.myCardDishes.clear()
                                myCardDishes.myCardDishes.addAll(it)
                                _lists.value = AppStateMyCard.OnSuccess(it, myCardDishes)

                                totalPrice(it)
                            },
                                {
                                    _lists.value =
                                        it.message?.let { it1 -> AppStateMyCard.Error(it1) }

                                })
                    }, {
                        _lists.value = it.message?.let { it1 -> AppStateMyCard.Error(it1) }
                    })

            }
        }

        myCardDishes.itemClickListenerDecrease = {
            val key = myCardDishes.myCardDishes[it.pos].id
            val defaultValue = myCardDishes.myCardDishes[it.pos].count

            prices[key] = prices.getOrDefault(key, defaultValue) - 1

            prices[key]?.let { it1 ->


                if (it1 == 0) {
                    myCard.delete(myCardDishes.myCardDishes[it.pos])
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        myCard.getAllMyCard()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                myCardDishes.myCardDishes.clear()
                                myCardDishes.myCardDishes.addAll(it)
                                _lists.value = AppStateMyCard.OnUpdate(myCardDishes)

                                totalPrice(it)
                            },
                                {

                                    _lists.value =
                                        it.message?.let { it1 -> AppStateMyCard.Error(it1) }
                                })
                    }, {
                        _lists.value = it.message?.let { it1 -> AppStateMyCard.Error(it1) }
                    })
                } else {

                    myCard.update(key.toString(), it1).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            myCard.getAllMyCard()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    myCardDishes.myCardDishes.clear()
                                    myCardDishes.myCardDishes.addAll(it)
                                    _lists.value = AppStateMyCard.OnUpdate(myCardDishes)

                                    totalPrice(it)
                                },
                                    {

                                        _lists.value =
                                            it.message?.let { it1 -> AppStateMyCard.Error(it1) }
                                    })
                        }, {
                            _lists.value = it.message?.let { it1 -> AppStateMyCard.Error(it1) }
                        })
                }

            }
        }

        getAllDishesFromCard()
    }

    @SuppressLint("CheckResult")
    private fun getAllDishesFromCard() {
        myCard.getAllMyCard()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                myCardDishes.myCardDishes.clear()
                myCardDishes.myCardDishes.addAll(it)
                totalPrice(it)
                _lists.value = AppStateMyCard.OnSuccess(myCardDishes.myCardDishes, myCardDishes)

            }, {
                _lists.value = it.message?.let { it1 -> AppStateMyCard.Error(it1) }
            })
    }

    private fun totalPrice(myProducts: List<Dishe>) {
        var totalPrice = 0
        myProducts.forEach {
            totalPrice += it.price.toInt() * it.count
        }
        _lists.value = AppStateMyCard.TotalPrice(totalPrice.toString())

    }

}