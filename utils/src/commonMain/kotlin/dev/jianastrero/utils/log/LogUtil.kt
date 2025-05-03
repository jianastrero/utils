package dev.jianastrero.utils.log

import dev.jianastrero.utils.println
import dev.jianastrero.utils.reflect.getProperties
import dev.jianastrero.utils.trace.getCaller
import dev.jianastrero.utils.trace.getStackTrace

data object LogUtil {
    private const val DEFAULT_TAG = "JIANDDEBUG"

    internal const val QUALIFIED_CLASS_NAME = "dev.jianastrero.utils.log.LogUtilKt"

    var minLogLevel: LogLevel = LogLevel.ERROR
    var tag: String = DEFAULT_TAG
}

fun <T> T.log(
    tag: String = LogUtil.tag,
    level: LogLevel = LogLevel.DEBUG,
): T {
    if (level.level < LogUtil.minLogLevel.level) return this
    if (this == null) {
        println("$this is NULL", level = level)
        return this
    }

    if (this is Throwable) {
        val message = "(${this::class.qualifiedName}) ${this.message ?: "No ERROR message"}"
        println(message = message, tag = tag, level = LogLevel.ERROR)
        printStackTrace()
        return this
    }

    val logMessage = "(${this::class.qualifiedName}) $this"
    println(message = logMessage, tag = tag, level = level)
    return this
}

fun <T> T.logDeep(name: String, tag: String = LogUtil.tag, level: LogLevel = LogLevel.DEBUG): T {
    if (level.level < LogUtil.minLogLevel.level) return this
    if (this == null) {
        "$name is NULL".log(tag = tag, level = level)
        return this
    }

    val kClass = this::class
    val caller = getCaller()
    val className = kClass.qualifiedName ?: "Unknown Class"
    val propertiesList = getPropertyList(name = name, level = level)
    val callStack = getCallStack(name = name, level = level)

    val logMessage = deepLog {
        this.name = name
        this.className = className
        this.caller = caller
        this.value = this@logDeep
        this.properties = propertiesList ?: emptyList()
        this.callStack = callStack ?: emptyList()
    }

    println(
        message = "\n$logMessage",
        tag = tag,
        level = level,
    )

    return this
}

private inline fun <reified T> T.getPropertyList(
    name: String,
    level: LogLevel
): List<Triple<String, String, Any?>>? = runCatching { this?.getProperties() }
    .getOrElse { exception ->
        exception.log(name, level)
        null
    }?.map { (propertyName, property) ->
        val (propertyClass, propertyValue) = property
        Triple(
            propertyName,
            propertyClass,
            propertyValue
        )
    }

private inline fun <reified T> T.getCallStack(
    name: String,
    level: LogLevel
) = runCatching { getStackTrace() }
    .getOrElse { exception ->
        exception.log(name, level)
        null
    }
