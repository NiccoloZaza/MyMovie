@file:Suppress("unused")

package com.flatrock.glib.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatrock.networklib.NetworkController
import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.listeners.IOnConnectivityChangeListener
import com.flatrock.networklib.observers.NetworkObserver

abstract class BaseCompatActivity: AppCompatActivity(), IOnConnectivityChangeListener {
    override fun onConnectivityChanged(
        connectivity: Connectivity,
        connectivityStrength: ConnectivityStrength,
        connectivityType: ConnectivityType
    ) = Unit

    //region Fields
    private var networkObserver: NetworkObserver? = null

    protected val networkState: Connectivity
        get() = NetworkController.connectionState

    protected val networkAvailable: Boolean
        get() = NetworkController.isConnected
    //endregion

    //region Abstractions
    /**Layout Id Of Activity To Use SetContentView*/
    protected abstract val layoutId: Int
    //endregion

    //region Overridden Methods
    override fun onDestroy() {
        super.onDestroy()
        if (networkObserver != null)
            NetworkController.removeObserver(networkObserver!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        val declaringClass = this::class.java.getMethod(
            "onConnectivityChanged",
            Connectivity::class.java,
            ConnectivityStrength::class.java,
            ConnectivityType::class.java
        ).declaringClass
        val shouldMonitorNetwork: Boolean =
            declaringClass.name.compareTo(BaseCompatActivity::class.qualifiedName.toString()) != 0
        if (shouldMonitorNetwork) {
            networkObserver = NetworkObserver(this)
            NetworkController.addObserver(networkObserver!!)
        }
    }
    //endregion
}