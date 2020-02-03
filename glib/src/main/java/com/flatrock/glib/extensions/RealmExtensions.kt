package com.flatrock.glib.extensions

import io.realm.Realm
import io.realm.RealmObject

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Long?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Int?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Short?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Boolean?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Byte?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: ByteArray?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: String?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Float?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}

inline fun <reified T: RealmObject>
        Realm.createObjectOrUpdate(id: Double?): T {
    this.where(T::class.java).equalTo("id", id).findAll().deleteAllFromRealm()
    return this.createObject(T::class.java, id)
}