package com.flatrock.glib.transfermodels

internal abstract class BaseDTO<T> {
    var id: Long = 0

    abstract fun toModel(): T
}