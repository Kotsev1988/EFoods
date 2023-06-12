package com.example.category.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.category.presentation.viewmodel.appState.CategoryAppState
import com.example.core.domain.entity.Category
import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.MenuCategory
import com.example.core.domain.entity.Menus
import com.example.core.domain.entity.repository.IGetDishes
import com.example.core.domain.myCard.IMyCardProducts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    var productList: IGetDishes,
    private val myCard: IMyCardProducts,
) : ViewModel() {

    private val _lists: MutableLiveData<CategoryAppState> = MutableLiveData()
    val listItem: LiveData<CategoryAppState>
        get() {
            return _lists
        }

     val listMenu = ListMenu()
     val listDishes = ListDishes()

    var dishes: ArrayList<Dishe> = arrayListOf()

    val menus :HashSet<String> = hashSetOf()
    val menuCategory = MenuCategory()


    fun init() {

        listDishes.itemClickListener ={
            val dish = listDishes.dishes[it.pos]
            _lists.value = CategoryAppState.ShowDialogFragment(dish)
        }
                listMenu.itemClickListener = {

                    val index = it.pos

                    listMenu.menuCategory[index].select = true

                    listMenu.menuCategory.indices.forEach {
                        if (it != index) {
                            listMenu.menuCategory[it].select = false
                        }
                    }
                    val menu = listMenu.menuCategory[it.pos].name
                    loadDishesByMenu(menu)
                }

        loadAllDishes()
    }

    @SuppressLint("CheckResult")
    private fun loadDishesByMenu(menu: String) {
        productList.getDishes().observeOn(AndroidSchedulers.mainThread()).subscribe({

            listDishes.dishes.clear()

            dishes.clear()

            it.forEach {  dish ->
                dish.tegs.forEach {teg ->

                    if (menu.equals(teg)){
                        dishes.add(dish)
                    }
                }
            }

            listDishes.dishes.addAll(dishes)

            _lists.value = CategoryAppState.SetDishes(
                menuCategory = listMenu.menuCategory,
                listMenu = listMenu,
                dishes = dishes,
                listDishes = listDishes
            )
        },
            {
                _lists.value = it.message?.let { it1 -> CategoryAppState.Error(it1) }

            })
    }

    @SuppressLint("CheckResult")
    private fun loadAllDishes() {
        productList.getDishes().observeOn(AndroidSchedulers.mainThread()).subscribe({
            menus.clear()
            it.forEach {
                menus.addAll(it.tegs)
            }

            menus.forEach {
                menuCategory.add(Category(it, false))
            }
            listMenu.menuCategory.clear()
            listMenu.menuCategory.addAll(menuCategory)
            listDishes.dishes.addAll(it)


            _lists.value = CategoryAppState.OnSuccess(
                menuCategory = listMenu.menuCategory,
                listMenu = listMenu,
                dishes = listDishes.dishes,
                listDishes = listDishes
            )

        },
            {
                _lists.value = it.message?.let { it1 -> CategoryAppState.Error(it1) }
            })
    }

}