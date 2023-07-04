package com.example.domain.repository

import com.example.domain.entity.Dishe
import io.reactivex.rxjava3.core.Single

interface IGetDishes {
    fun getDishes(): Single<List<Dishe>>
}