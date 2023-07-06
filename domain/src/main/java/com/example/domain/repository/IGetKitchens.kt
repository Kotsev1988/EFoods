package com.example.domain.repository

import com.example.domain.entity.Categories
import retrofit2.Response

interface IGetKitchens {
    suspend fun getKitchens(): Response<Categories>
}