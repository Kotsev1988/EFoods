package com.example.core.domain.entity.repository

import com.example.core.domain.entity.СategoryKitchen
import io.reactivex.rxjava3.core.Single

interface IGetKitchens {
    fun getKitchens():Single<List<СategoryKitchen>>
}