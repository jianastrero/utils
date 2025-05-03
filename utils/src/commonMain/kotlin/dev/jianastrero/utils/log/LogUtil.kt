package dev.jianastrero.utils.log

import dev.jianastrero.utils.println
import dev.jianastrero.utils.reflect.getProperties
import dev.jianastrero.utils.trace.getCaller
import dev.jianastrero.utils.trace.getStackTrace

data object LogUtil {
    private const val DEFAULT_TAG = "JIANDDEBUG"

    internal const val FILE_NAME = "LogUtil.kt"
    internal const val METHOD_NAME = "logDeep"

    var minLogLevel: LogLevel = LogLevel.ERROR
    val tag: String = DEFAULT_TAG
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
        println(stackTraceToString(), tag = tag, level = LogLevel.ERROR)
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
    val properties = runCatching { this.getProperties() }
        .getOrElse { exception ->
            exception.log(name, level)
            null
        }
    val callStack = runCatching { getStackTrace() }
        .getOrElse { exception ->
            exception.log(name, level)
            null
        }

    val logMessage = StringBuilder()
        .appendLine()
        .appendLine(caller)
        .appendLine("| $name ($className)")
        .apply {
            properties?.forEach { (propertyName, property) ->
                val (propertyClass, propertyValue) = property
                appendLine("⌙ $propertyName ($propertyClass) | $propertyValue")
            }
        }
        .appendLine("Call Stack:")
        .apply {
            callStack?.forEachIndexed { index, stack ->
                appendLine("${" ".repeat(index + 1)}⌙ $stack")
            }
        }
        .toString()
    println(
        message = logMessage,
        tag = tag,
        level = level,
    )

    return this
}
