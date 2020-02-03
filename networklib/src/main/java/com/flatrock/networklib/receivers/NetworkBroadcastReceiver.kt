package com.flatrock.networklib.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.flatrock.networklib.NetworkController

class NetworkBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        NetworkController.connectivityChanged()
    }
}