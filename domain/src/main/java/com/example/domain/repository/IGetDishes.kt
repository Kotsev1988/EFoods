package com.example.domain.repository

import com.example.domain.entity.Menus
import retrofit2.Response

interface IGetDishes {
   suspend fun getDishes(): Response<Menus>
}