/*
 * Copyright (c) Jian James Astrero 2025
 */

package dev.jianastrero.utils.ext

inline fun String?.truthy(): Boolean = this != null
inline fun Boolean?.truthy(): Boolean = this == true
inline fun Char?.truthy(): Boolean = this != '\u0000'
inline fun Float?.truthy(): Boolean = this != null && !isNaN() && this != 0f
inline fun Double?.truthy(): Boolean = this != null && !isNaN() && this != 0.0
inline fun Number?.truthy(): Boolean = this != null && this != 0.0
inline fun Collection<*>.truthy(): Boolean = isNotEmpty()
inline fun Map<*, *>.truthy(): Boolean = isNotEmpty()
inline fun Sequence<*>.truthy(): Boolean = iterator().hasNext()
inline fun Iterable<*>.truthy(): Boolean = iterator().hasNext()
inline fun Array<*>.truthy(): Boolean = isNotEmpty()
inline fun BooleanArray.truthy(): Boolean = isNotEmpty()
inline fun ByteArray.truthy(): Boolean = isNotEmpty()
inline fun CharArray.truthy(): Boolean = isNotEmpty()
inline fun ShortArray.truthy(): Boolean = isNotEmpty()
inline fun IntArray.truthy(): Boolean = isNotEmpty()
inline fun FloatArray.truthy(): Boolean = isNotEmpty()
inline fun DoubleArray.truthy(): Boolean = isNotEmpty()
inline fun ClosedRange<*>.truthy(): Boolean = !isEmpty()
inline fun OpenEndRange<*>.truthy(): Boolean = !isEmpty()
inline fun <T> T?.truthy(): Boolean = this != null
