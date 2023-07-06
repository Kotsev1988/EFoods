package com.example.category.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.category.presentation.view.ListDishes
import com.example.category.presentation.view.ListMenu
import com.example.category.presentation.viewmodel.appState.CategoryAppState
import com.example.core.network.INetworkStates
import com.example.data.room.cache.IDishesCache
import com.example.domain.entity.Category
import com.example.domain.entity.Dishe
import com.example.domain.entity.MenuCategory
import com.example.domain.entity.Menus
import com.example.domain.repository.IGetDishes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Provider

class CategoryViewModel(
    var productList: IGetDishes,
    private val networkStatus: INetworkStates,
    private val productCache: IDishesCache,
) : ViewModel() {

    private val _lists: MutableLiveData<CategoryAppState> = MutableLiveData()
    val listItem: LiveData<CategoryAppState>
        get() {
            return _lists
        }

    private val listMenu = ListMenu()
    private val listDishes = ListDishes()

    private var dishes: ArrayList<Dishe> = arrayListOf()

    private val menus: HashSet<String> = hashSetOf()
    private val menuCategory = MenuCategory()

    fun init() {
        viewModelScope.launch {
            networkStatus.isOnline().collect {status ->
                when (status) {

                    INetworkStates.Status.Available -> {
                        loadAllDishesResponse()
                    }

                    INetworkStates.Status.UnAvailable -> {
                        _lists.value = CategoryAppState.UnAvailable("Unavailable")
                        loadDishesFromCache()
                    }

                    INetworkStates.Status.Losing -> {
                        _lists.value = CategoryAppState.Losing("Losing")
                    }

                    INetworkStates.Status.Lost -> {
                        _lists.value = CategoryAppState.Lost("Lost")

                    }
                }
            }
        }

        listDishes.itemClickListener = {
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

            viewModelScope.launch {
                networkStatus.isOnline().collect { status ->
                    when (status) {

                        INetworkStates.Status.Available -> {
                            loadDishesByMenu(menu)
                        }

                        INetworkStates.Status.UnAvailable -> {
                            loadDishesByMenuFromCache(menu)
                        }

                        INetworkStates.Status.Losing -> {
                            //loadDishesByMenuFromCache(menu)
                        }

                        INetworkStates.Status.Lost -> {
                            //loadDishesByMenuFromCache(menu)
                        }
                    }
                }
            }
        }
    }

    private fun loadDishesByMenuFromCache(menu: String) {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val listOfDishes = productCache.getDishesFromCache()

                listDishes.dishes.clear()

                dishes.clear()

                listOfDishes.forEach { dish ->
                    dish.tegs.forEach { teg ->

                        if (menu.equals(teg)) {
                            dishes.add(dish)
                        }
                    }
                }

                listDishes.dishes.addAll(dishes)
            }

            withContext(Dispatchers.Main) {
                _lists.value = CategoryAppState.SetDishes(
                    menuCategory = listMenu.menuCategory,
                    listMenu = listMenu,
                    dishes = dishes,
                    listDishes = listDishes
                )
            }

        }
    }

    fun loadDishesFromCache() = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productListFromCache = productCache.getDishesFromCache()


                menus.clear()
                productListFromCache.forEach {
                    menus.addAll(it.tegs)
                }

                menus.forEach {
                    menuCategory.add(Category(it, false))
                }
                listMenu.menuCategory.clear()
                listMenu.menuCategory.addAll(menuCategory)
                productListFromCache.let { listDishes.dishes.addAll(it) }
            }


            withContext(Dispatchers.Main) {
                _lists.value = CategoryAppState.OnSuccess(
                    menuCategory = listMenu.menuCategory,
                    listMenu = listMenu,
                    dishes = listDishes.dishes,
                    listDishes = listDishes
                )
            }

        }


    @SuppressLint("CheckResult")
    private fun loadDishesByMenu(menu: String) = viewModelScope.launch {
            val listOfDishes = productList.getDishes()

            listDishes.dishes.clear()

            dishes.clear()

            if (listOfDishes.isSuccessful) {
                val dishesBody = listOfDishes.body()

                dishesBody?.dishes?.forEach { dish ->
                    dish.tegs.forEach { teg ->

                        if (menu.equals(teg)) {
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
            } else {
                _lists.value = CategoryAppState.Error("Error Response")

            }
        }


    @SuppressLint("CheckResult")
    fun loadAllDishesResponse() = viewModelScope.launch {
        val response = productList.getDishes()

        loadAllDishes(response)

        if (response.isSuccessful) {
            response.body()?.let { productCache.insertDishesToDB(it.dishes) }
        }

    }

    private fun loadAllDishes(response: Response<Menus>) {
        if (response.isSuccessful) {

            val responseMenus = response.body()
            menus.clear()
            responseMenus?.dishes?.forEach {
                menus.addAll(it.tegs)
            }

            menus.forEach {
                menuCategory.add(Category(it, false))
            }
            listMenu.menuCategory.clear()
            listMenu.menuCategory.addAll(menuCategory)
            responseMenus?.dishes?.let { listDishes.dishes.addAll(it) }



            _lists.value = CategoryAppState.OnSuccess(
                menuCategory = listMenu.menuCategory,
                listMenu = listMenu,
                dishes = listDishes.dishes,
                listDishes = listDishes
            )

        } else {
            _lists.value = CategoryAppState.Error("Response error")
        }
    }

    class Factory @Inject constructor(
        private val productList: Provider<IGetDishes>,
        private val networkStatus: Provider<INetworkStates>,
        private val productCache: Provider<IDishesCache>,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CategoryViewModel::class.java)
            return CategoryViewModel(
                productList.get(),
                networkStatus.get(),
                productCache.get()
            ) as T
        }
    }

}