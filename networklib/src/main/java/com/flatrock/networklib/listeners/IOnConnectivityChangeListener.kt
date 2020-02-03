package com.flatrock.networklib.listeners

import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType

interface IOnConnectivityChangeListener {
    fun onConnectivityChanged(connectivity: Connectivity, connectivityStrength: ConnectivityStrength, connectivityType: ConnectivityType)
}