package com.flatrock.glib.annotations

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.FIELD)
annotation class InjectView(val id: Int = -1)