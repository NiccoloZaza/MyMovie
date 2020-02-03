package com.flatrock.glib.realm.controllers

import io.realm.Realm

internal abstract class BaseRealmController {
    val realm: Realm
        get() = Realm.getDefaultInstance()
}