package com.example.efoods.presentation.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.efoods.App
import com.example.efoods.presentation.viewModel.appState.AppStateLocation
import kotlinx.coroutines.launch
import java.io.IOException

private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class BaseViewModel() : ViewModel() {

    private val _liveDataLocation: MutableLiveData<AppStateLocation> = MutableLiveData()
    val liveDataLocation: LiveData<AppStateLocation> = _liveDataLocation

    private var city = ""

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            App.instance.getAppContext().let {
                viewModelScope.launch {
                    getAddressAsync(it, location)
                }

            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }
    }

    fun getLocation() {

     //  LocationServices.getFusedLocationProviderClient(App.instance.getAppContext())

        App.instance.getAppContext().let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {

                val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
                        as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        _liveDataLocation.value = AppStateLocation.EmptyData("Empty")
                    } else {
                        viewModelScope.launch {
                            getAddressAsync(context, location)
                        }

                    }
                }
            } else {
                _liveDataLocation.value = AppStateLocation.ShowRationalDialog("show")
            }
        }
    }


     fun getAddressAsync(context: Context, location: Location) {

        val geoCoder = Geocoder(context)


            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                val cityName = addresses?.get(0)?.locality
                city = cityName.toString()
                _liveDataLocation.value = AppStateLocation.Success(city)

            } catch (e: IOException) {

                //_liveDataLocation.value = AppStateLocation.Error(it)


        }

    }
}