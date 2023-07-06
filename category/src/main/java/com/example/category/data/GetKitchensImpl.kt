package com.example.category.data

import com.example.api.IFoodAPI
import com.example.domain.entity.Categories
import com.example.domain.repository.IGetKitchens
import retrofit2.Response

class GetKitchensImpl(
    private val api: IFoodAPI,
) : IGetKitchens {

    override suspend fun getKitchens(): Response<Categories> = api.getKitchens()

}