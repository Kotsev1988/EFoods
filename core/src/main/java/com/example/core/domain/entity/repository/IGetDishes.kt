package com.example.core.domain.entity.repository

import com.example.core.domain.entity.Dishe
import com.example.core.domain.entity.СategoryKitchen
import io.reactivex.rxjava3.core.Single

interface IGetDishes {
    fun getDishes(): Single<List<Dishe>>
}