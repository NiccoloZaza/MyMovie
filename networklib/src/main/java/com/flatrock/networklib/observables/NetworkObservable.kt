package com.flatrock.networklib.observables

import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.observers.NetworkObserver
import java.util.*

class NetworkObservable : Observable() {
    override fun hasChanged(): Boolean {
        return true
    }

    fun connectivityChanged(connectivity: Connectivity, connectivityStrength: ConnectivityStrength, connectivityType: ConnectivityType) {
        notifyObservers(NetworkObserver.NetworkParams(connectivity, connectivityStrength, connectivityType))
    }
}