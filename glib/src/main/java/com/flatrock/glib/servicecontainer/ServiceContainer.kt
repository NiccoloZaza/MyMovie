@file:Suppress("unused")

package com.flatrock.glib.servicecontainer

import java.lang.Exception
import kotlin.reflect.KClass

object ServiceContainer {
    private var services: HashMap<KClass<*>, Any> = HashMap()

    fun <T : Any, M : T> registerService(service: KClass<T>, implementation: M) {
        if (services[service] != null) {
            throw Exception("Service has been already registered")
        }
        services[service] = implementation
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getService(service: KClass<T>) : T? {
        if (services[service] == null) {
            throw Exception("Service \"" + service.simpleName + "\" is not registered")
        }
        return try {
            services[service] as T
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}