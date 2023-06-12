package com.example.core.domain.myCard

import com.example.core.domain.entity.Dishe
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMyCardProducts {
    fun getAllMyCard(): Single<List<Dishe>>
    fun insertProductToMyCard(productsItem: Dishe): Single<Dishe>
    fun isContain(id: Int): Single<Boolean>
    fun update(id: String, count: Int): Completable
    fun delete(id: Dishe): Completable
    fun getDishById(id: Int): Single<Dishe>
}