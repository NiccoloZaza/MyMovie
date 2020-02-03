@file:Suppress("unused")

package com.flatrock.glib.servicecontainer

class Executioner<T> {
    private var serviceExecutioner: ServiceExecutioner? = null

    private var executable: ((result: T)->Unit)? = null
    private var errorExecutable: ((result: Exception)->Unit)? = null

    internal fun setServiceExecutioner(serviceExecutioner: (()->Unit)): Executioner<T> {
        this.serviceExecutioner =
            ServiceExecutioner()
        this.serviceExecutioner?.setServiceExecuter(serviceExecutioner)
        return this
    }

    fun onPostExecute(executable: (result: T)->Unit): Executioner<T>? {
        this.executable = executable
        return this
    }

    fun onError(executable: (result: Exception)->Unit): Executioner<T>? {
        this.errorExecutable = executable
        return this
    }

    fun execute() {
        this.serviceExecutioner?.execute()
    }

    internal fun postExecute(result: T) {
        executable?.invoke(result)
    }

    internal fun postError(result: Exception) {
        errorExecutable?.invoke(result)
    }
}

class VoidExecutioner {
    private var serviceExecutioner: ServiceExecutioner? = null

    private var executable: (()->Unit)? = null
    private var errorExecutable: ((result: Exception)->Unit)? = null

    internal fun setServiceExecutioner(serviceExecutioner: (()->Unit)): VoidExecutioner {
        this.serviceExecutioner =
            ServiceExecutioner()
        this.serviceExecutioner?.setServiceExecuter(serviceExecutioner)
        return this
    }

    fun onPostExecute(executable: ()->Unit): VoidExecutioner? {
        this.executable = executable
        return this
    }

    fun onError(executable: (result: Exception)->Unit): VoidExecutioner? {
        this.errorExecutable = executable
        return this
    }

    fun execute() {
        this.serviceExecutioner?.execute()
    }

    internal fun postExecute() {
        executable?.invoke()
    }

    internal fun postError(result: Exception) {
        errorExecutable?.invoke(result)
    }
}
