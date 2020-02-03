@file:Suppress("unused")

package com.flatrock.networklib

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.observables.NetworkObservable
import com.flatrock.networklib.observers.NetworkObserver
import com.flatrock.networklib.receivers.NetworkBroadcastReceiver
import java.lang.reflect.Method

object NetworkController {
    //region Fields
    private lateinit var applicationContext: Context

    private var firstCall: Boolean = true

    private var alreadyInitialized: Boolean = false

    private val initLock: Any = Any()

    private val networkObservable: NetworkObservable = NetworkObservable()

    private val intentFilter: IntentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    private val networkReceiver: NetworkBroadcastReceiver = NetworkBroadcastReceiver()
    //endregion

    //region Properties
    val isConnected: Boolean
        get() = connectionState == Connectivity.Connected

    val connectionState: Connectivity
        get() {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return if (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
                Connectivity.Connected
            else
                Connectivity.NotConnected
        }

    val connectionStrength: ConnectivityStrength
        @SuppressLint("WifiManagerPotentialLeak")
        get() {
            when (connectionType) {
                ConnectivityType.Cellular -> {
                    return when (getCellularStrength()) {
                        0 -> ConnectivityStrength.None
                        1 -> ConnectivityStrength.Poor
                        2 -> ConnectivityStrength.Normal
                        3 -> ConnectivityStrength.Excellent
                        else -> ConnectivityStrength.Unknown
                    }
                }
                ConnectivityType.Wifi -> {
                    val wifiManager: WifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val wifiInfo: WifiInfo = wifiManager.connectionInfo
                    return when (WifiManager.calculateSignalLevel(wifiInfo.rssi, 5)) {
                        0 -> ConnectivityStrength.None
                        1,2 -> ConnectivityStrength.Poor
                        3,4 -> ConnectivityStrength.Normal
                        5 -> ConnectivityStrength.Excellent
                        else -> ConnectivityStrength.Unknown
                    }
                }
                else -> return ConnectivityStrength.Unknown
            }
        }

    val connectionType: ConnectivityType
        get() {
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return if (capabilities != null)
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectivityType.Wifi
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityType.Cellular
                    else ->
                        ConnectivityType.None
                }
            else
                ConnectivityType.None
        }
    //endregion

    //region Custom Methods
    private fun getCellularStrength() : Int {
        try {
            return getCellularStrengthReflect()
        } catch(exception: Exception) {
            when ((applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkType) {
                16,
                TelephonyManager.NETWORK_TYPE_GPRS,
                TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_1xRTT,
                TelephonyManager.NETWORK_TYPE_IDEN,
                TelephonyManager.NETWORK_TYPE_CDMA -> return 1

                17,
                TelephonyManager.NETWORK_TYPE_UMTS,
                TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_EVDO_A,
                TelephonyManager.NETWORK_TYPE_HSDPA,
                TelephonyManager.NETWORK_TYPE_HSUPA,
                TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_EVDO_B,
                TelephonyManager.NETWORK_TYPE_EHRPD,
                TelephonyManager.NETWORK_TYPE_HSPAP -> return 2

                18,
                TelephonyManager.NETWORK_TYPE_LTE -> return 3

                else -> return 0
            }
        }
    }

    private fun getCellularStrengthReflect() : Int {
        val method: Method = TelephonyManager::class.java.getDeclaredMethod("getNetworkClass", Int::class.java)
        val accessible = method.isAccessible
        method.isAccessible = true
        val strength: Int = method.invoke(null, (applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkType) as Int
        method.isAccessible = accessible
        return strength
    }

    fun init(applicationContext: Context) {
        if (alreadyInitialized)
            return
        synchronized(initLock) {
            if (alreadyInitialized)
                return
            alreadyInitialized = true
            this.applicationContext = applicationContext
        }
    }

    fun addObserver(networkObserver: NetworkObserver) {
        networkObservable.addObserver(networkObserver)
        if (networkObservable.countObservers() == 1) {
            applicationContext.registerReceiver(networkReceiver, intentFilter)
        }
    }

    fun removeObserver(networkObserver: NetworkObserver) {
        networkObservable.deleteObserver(networkObserver)
        if (networkObservable.countObservers() == 0) {
            applicationContext.unregisterReceiver(networkReceiver)
        }
    }

    internal fun connectivityChanged() {
        if (firstCall) {
            firstCall = false
            return
        }
        networkObservable.connectivityChanged(connectionState, connectionStrength, connectionType)
    }
    //endregion
}