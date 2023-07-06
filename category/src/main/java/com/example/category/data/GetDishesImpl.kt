package com.example.category.data

import com.example.api.IFoodAPI
import com.example.domain.entity.Menus
import com.example.domain.repository.IGetDishes
import retrofit2.Response

class GetDishesImpl(private val api: IFoodAPI
): IGetDishes {
    override suspend fun getDishes(): Response<Menus> = api.getDishes()

}