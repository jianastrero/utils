package dev.jianastrero.utils.log

import dev.jianastrero.utils.println
import dev.jianastrero.utils.reflect.getProperties
import dev.jianastrero.utils.trace.getCaller
import dev.jianastrero.utils.trace.getStackTrace
import kotlin.math.max

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
    val propertiesList = runCatching { this.getProperties() }
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
    val callStack = runCatching { getStackTrace() }
        .getOrElse { exception ->
            exception.log(name, level)
            null
        }

    val logMessage = StringBuilder()
        .appendLine()
        .appendLine(caller)
        .apply {
            val maxNameLength = propertiesList?.maxOfOrNull { it.first.length } ?: 0
            val maxClassLength = propertiesList?.maxOfOrNull { it.second.length } ?: 0
            val maxValueLength = propertiesList?.maxOfOrNull { it.third.toString().length } ?: 0
            val headerLength = name.length + className.length + "|  () |".length

            val stringLength = maxNameLength + maxClassLength + maxValueLength
            val totalLength = max(headerLength, stringLength + "|  |  |  |".length)

            hr(totalLength)
            header("$name ($className)", totalLength)
            hr(totalLength)

            if (propertiesList == null || propertiesList.isEmpty()) {
                value("value | ${this@logDeep}", totalLength)
            } else {
                propertiesList.forEach { (propertyName, propertyClass, propertyValue) ->
                    val namePadding = " ".repeat(maxNameLength - propertyName.length)
                    val classPadding = " ".repeat(maxClassLength - propertyClass.length)
                    val valuePadding = " ".repeat(maxValueLength - propertyValue.toString().length)
                    appendLine("| $propertyName$namePadding | $propertyClass$classPadding | $propertyValue$valuePadding |")
                }
            }
            hr(totalLength)
        }
        .appendLine("Call Stack:")
        .apply {
            callStack?.forEachIndexed { index, stack ->
                appendLine("${" ".repeat(index + 1)}↳ $stack")
            }
        }
        .toString()
        .trim()

    println(
        message = logMessage,
        tag = tag,
        level = level,
    )

    return this
}

private fun StringBuilder.hr(length: Int): StringBuilder {
    return appendLine("-".repeat(length))
}

private fun StringBuilder.header(
    text: String,
    length: Int
): StringBuilder {
    val padding = " ".repeat(length - text.length - "|  |".length)
    return appendLine("| $text$padding |")
}

private fun StringBuilder.value(
    text: String,
    length: Int
): StringBuilder {
    val padding = " ".repeat(length - text.length - "|  |".length)
    return appendLine("| $text$padding |")
}
