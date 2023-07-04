package com.example.domain.repository

import com.example.domain.entity.СategoryKitchen
import io.reactivex.rxjava3.core.Single

interface IGetKitchens {
    fun getKitchens():Single<List<СategoryKitchen>>
}