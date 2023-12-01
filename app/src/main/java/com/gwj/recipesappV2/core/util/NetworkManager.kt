package com.gwj.recipesappV2.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class NetworkManager(context: Context): LiveData<Boolean>() {
    // Called when the LiveData is active
    override fun onActive() {
        super.onActive()
        // Check the network connectivity when the LiveData becomes active
        checkNetworkConnectivity()
    }

    // Called when the LiveData is inactive, i.e., doesn't have any active observers
    override fun onInactive() {
        super.onInactive()
        // Unregister the network callback when the LiveData becomes inactive
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    // Get the connectivity manager from the system services
    private var connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Define a network callback to handle network changes
    private val networkCallback = object :ConnectivityManager.NetworkCallback(){

        // Called when the network is available
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        // Called when the network is unavailable
        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }

        // Called when the network is lost
        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    // Check the network connectivity
    private fun checkNetworkConnectivity(){
        // Get the active network
        val network = connectivityManager.activeNetwork
        // If the network is null, post a false value to the LiveData
        if(network == null) postValue(false)

        // Build a network request
        val requestBuilder = NetworkRequest.Builder().apply {
            addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        }.build()

        // Register a network callback to listen to network changes
        connectivityManager.registerNetworkCallback(requestBuilder, networkCallback)
    }
}