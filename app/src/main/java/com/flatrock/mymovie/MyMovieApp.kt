package com.flatrock.mymovie

import android.app.Application
import android.widget.Toast
import com.flatrock.glib.ApplicationInstance
import com.flatrock.networklib.NetworkController
import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.listeners.IOnConnectivityChangeListener
import com.flatrock.networklib.observers.NetworkObserver

class MyMovieApp: Application(), IOnConnectivityChangeListener {
    private var networkObserver: NetworkObserver? = null

    override fun onCreate() {
        super.onCreate()
        ApplicationInstance.init(applicationContext)
        networkObserver = NetworkObserver(this)
        NetworkController.addObserver(networkObserver!!)
    }

    override fun onConnectivityChanged(
        connectivity: Connectivity,
        connectivityStrength: ConnectivityStrength,
        connectivityType: ConnectivityType
    ) {
        if (connectivity == Connectivity.NotConnected) {
            Toast.makeText(applicationContext, R.string.no_internet, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, R.string.connection_established, Toast.LENGTH_LONG).show()
        }
    }
}