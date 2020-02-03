package com.flatrock.networklib.observers

import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.listeners.IOnConnectivityChangeListener
import java.util.*

class NetworkObserver(private val OnConnectivityChangeListener: IOnConnectivityChangeListener) :
    Observer {
    internal class NetworkParams(val connectivityState: Connectivity,
                                 val connectivityStrength: ConnectivityStrength,
                                 val connectivityType: ConnectivityType
    )
    override fun update(p0: Observable?, p1: Any?) {
        p1 as NetworkParams
        OnConnectivityChangeListener.onConnectivityChanged(p1.connectivityState, p1.connectivityStrength, p1.connectivityType)
    }
}