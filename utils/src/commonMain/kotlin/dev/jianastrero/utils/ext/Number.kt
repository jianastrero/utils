package dev.jianastrero.utils.ext

fun Byte?.orZero() = this ?: 0
fun Short?.orZero() = this ?: 0
fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L
fun Float?.orZero() = this ?: 0f
fun Double?.orZero() = this ?: 0.0
