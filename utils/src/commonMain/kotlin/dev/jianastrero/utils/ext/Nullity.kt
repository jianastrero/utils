/*
 * Copyright (c) Jian James Astrero 2025
 */

package dev.jianastrero.utils.ext

@Suppress("CyclomaticComplexMethod")
fun <T> T?.truthy(): Boolean {
    return when (this) {
        null -> false
        is Boolean -> this

        is Char -> this != '\u0000'
        is String -> isNotEmpty()
        is CharSequence -> isNotEmpty()

        is Float -> !isNaN() && this != 0f
        is Double -> !isNaN() && this != 0.0
        is Number -> this != 0

        is Collection<*> -> isNotEmpty()
        is Map<*, *> -> isNotEmpty()
        is Sequence<*> -> iterator().hasNext()
        is Iterable<*> -> iterator().hasNext()

        is Array<*> -> isNotEmpty()
        is BooleanArray -> isNotEmpty()
        is ByteArray -> isNotEmpty()
        is CharArray -> isNotEmpty()
        is ShortArray -> isNotEmpty()
        is IntArray -> isNotEmpty()
        is FloatArray -> isNotEmpty()
        is DoubleArray -> isNotEmpty()

        is ClosedRange<*> -> !isEmpty()
        is OpenEndRange<*> -> !isEmpty()
        else -> true
    }
}
