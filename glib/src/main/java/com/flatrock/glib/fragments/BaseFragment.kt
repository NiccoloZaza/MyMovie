package com.flatrock.glib.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flatrock.networklib.NetworkController
import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import com.flatrock.networklib.listeners.IOnConnectivityChangeListener
import com.flatrock.networklib.observers.NetworkObserver

abstract class BaseFragment : Fragment(), IOnConnectivityChangeListener {
    //region Fields
    private var networkObserver: NetworkObserver? = null

    protected val networkAvailable: Connectivity
        get() = NetworkController.connectionState
    //endregion

    //region Abstracts
    /**Layout Id Of Fragment To Instantiate View*/
    protected abstract val layoutId: Int
    //endregion

    //region Overridden Methods
    override fun onDestroy() {
        super.onDestroy()
        if (networkObserver != null)
            NetworkController.removeObserver(networkObserver!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val declaringClass = this::class.java.getMethod("onConnectivityChanged", Connectivity::class.java, ConnectivityStrength::class.java, ConnectivityType::class.java).declaringClass
        val shouldMonitorNetwork: Boolean = declaringClass.name.compareTo(BaseFragment::class.qualifiedName.toString()) != 0
        if (shouldMonitorNetwork) {
            networkObserver = NetworkObserver(this)
            NetworkController.addObserver(networkObserver!!)
        }
    }
    //endregion

    //region Virtual Fields/Methods
    /**Tag Of Fragment For Navigation Purposes*/
    open var fragmentTag: String? = null

    override fun onConnectivityChanged(connectivity: Connectivity, connectivityStrength: ConnectivityStrength, connectivityType: ConnectivityType) {}
    //endregion
}