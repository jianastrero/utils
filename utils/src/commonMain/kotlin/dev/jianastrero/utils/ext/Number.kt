package dev.jianastrero.utils.ext

@Suppress("UNCHECKED_CAST")
fun <T : Number> T?.orZero(): T {
    if (this == null) {
        // Return zero of the appropriate type for null values
        return (0 as T)
    }

    // For non-null values, check if it's a supported type
    return when (this) {
        is Byte, is Short, is Int, is Long, is Float, is Double -> this
        else -> error("Cannot convert $this to zero")
    }
}
