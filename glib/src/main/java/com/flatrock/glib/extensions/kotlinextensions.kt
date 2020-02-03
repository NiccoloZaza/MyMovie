package com.flatrock.glib.extensions



inline fun <reified T> T?.ifNotNull(action: (value: T) -> Unit) {
    if (this != null && this is T)
        action.invoke(this)
}