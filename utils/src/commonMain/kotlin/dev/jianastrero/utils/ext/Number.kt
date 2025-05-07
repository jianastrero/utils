package dev.jianastrero.utils.ext

@Suppress("UNCHECKED_CAST")
fun <T : Number> T?.orZero(): T {
    return when (this) {
        is Byte -> 0.toByte()
        is Short -> 0.toShort()
        is Int -> 0
        is Long -> 0
        is Float -> 0
        is Double -> 0.0
        else -> error("Cannot convert $this to zero")
    } as T
}
