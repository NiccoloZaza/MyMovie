package com.flatrock.glib.servicecontainer

class ServiceExecutioner {
    private var serviceExecuter: (()->Unit)? = null

    internal fun setServiceExecuter(serviceExecuter: (()->Unit)): ServiceExecutioner {
        this.serviceExecuter = serviceExecuter
        return this
    }

    fun execute() {
        if (serviceExecuter != null) serviceExecuter?.invoke()
    }
}