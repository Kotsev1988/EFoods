package com.example.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AndroidNetworkStatus(context: Context): INetworkStates{

    private val statusObject : BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        statusObject.onNext(false)
        val connectivityManger = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()

        connectivityManger.registerNetworkCallback(request, object :
            ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                statusObject.onNext(true)
            }

            override fun onUnavailable() {
                statusObject.onNext(false)
            }

            override fun onLost(network: Network) {
                statusObject.onNext(false)
            }

        })
    }

    override fun isOnline() = statusObject

    override fun isOnlineSingle() = statusObject.first(false)
}