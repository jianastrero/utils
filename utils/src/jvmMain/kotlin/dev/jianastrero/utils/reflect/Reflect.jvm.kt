package dev.jianastrero.utils.reflect

import kotlin.reflect.full.memberProperties

@Throws(IllegalStateException::class)
actual fun <T : Any> T.getProperties(): Map<String, Pair<String, Any?>> {
    val kClass = this::class
    val properties = kClass.memberProperties
        .filter { it.name != "Companion" }
        .associate { property ->
            val name = property.name
            val value = property.getter.call(this)
            val valueClass = value?.let { it::class.qualifiedName } ?: "Unknown Class"
            name to (valueClass to value)
        }
    return properties
}
