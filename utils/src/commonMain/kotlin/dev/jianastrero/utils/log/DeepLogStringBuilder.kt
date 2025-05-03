package dev.jianastrero.utils.log

import dev.jianastrero.utils.format.format
import dev.jianastrero.utils.format.formatEmpty
import dev.jianastrero.utils.println
import kotlin.math.max

internal class DeepLogStringBuilder {
    var caller: String = ""
    var name: String = ""
    var className: String = ""
    var callStack: List<String> = emptyList()
    var value: Any? = null
    var properties: List<Triple<String, String, Any?>> = emptyList()

    companion object {
        const val HEADER_FORMAT = "| %s [%s]%s |"
        const val PROPERTY_FORMAT = "| %s | %s | %s |"
        const val VALUE_FORMAT = "| %s | %s |"
    }
}

private const val CLASS_STRING = "kotlin.String"

internal fun deepLog(block: DeepLogStringBuilder.() -> Unit): String {
    val builder = DeepLogStringBuilder().apply(block)

    val className = builder.className.ifEmpty { "Unknown Class" }
    val name = builder.name.ifEmpty { "Unknown Name" }
    val callStack = builder.callStack.ifEmpty { listOf("Unknown Call Stack") }
    val properties = if (builder.value is String) {
        builder.properties + Triple("value", String::class.qualifiedName ?: CLASS_STRING, builder.value)
    } else {
        builder.properties
    }

    val valueString = if (properties.isEmpty()) builder.value?.toString().orEmpty() else ""

    val maxNameLength = properties.maxOfOrNull { it.first.length } ?: 0
    val maxClassLength = properties.maxOfOrNull { it.second.length } ?: 0
    val maxValueLength = properties.maxOfOrNull { it.third.toString().length } ?: 0

    val headerFormatLength = DeepLogStringBuilder.HEADER_FORMAT.formatEmpty().length
    val headerLength = name.length + className.length + headerFormatLength

    val propertyFormatLength = DeepLogStringBuilder.PROPERTY_FORMAT.formatEmpty().length
    val propertyLength = maxNameLength + maxClassLength + maxValueLength + propertyFormatLength

    val valueFormatLength = DeepLogStringBuilder.VALUE_FORMAT.formatEmpty().length
    val valueLength = "value".length + valueString.length + valueFormatLength

    val totalLength = maxOf(headerLength, propertyLength, valueLength)

    return StringBuilder()
        .appendLine(builder.caller)
        .hr(totalLength)
        .header(name, className, totalLength, headerFormatLength)
        .hr(totalLength)
        .apply {
            if (properties.isEmpty()) {
                value("value", valueString, totalLength, valueFormatLength)
            } else {
                properties.forEach { (propertyName, propertyClass, propertyValue) ->
                    property(propertyName, propertyClass, propertyValue, maxNameLength, maxClassLength, maxValueLength)
                }
            }
        }
        .hr(totalLength)
        .apply {
            callStack.forEachIndexed { index, stack ->
                appendLine("${" ".repeat(index + 1)}↳ $stack")
            }
        }
        .toString()
        .trim()
}

private fun StringBuilder.hr(length: Int): StringBuilder {
    return appendLine("-".repeat(length))
}

private fun StringBuilder.header(
    name: String,
    className: String,
    totalLength: Int,
    formatLength: Int,
): StringBuilder {
    val padding = " ".repeat(totalLength - name.length - className.length - formatLength)

    return appendLine(
        DeepLogStringBuilder.HEADER_FORMAT.format(
            name,
            className,
            padding
        ).formatEmpty()
    )
}

private fun StringBuilder.value(
    propertyName: String,
    propertyValue: String,
    totalLength: Int,
    formatLength: Int,
): StringBuilder {
    val padding = " ".repeat(totalLength - propertyName.length - propertyValue.length - formatLength)

    return appendLine(
        DeepLogStringBuilder.VALUE_FORMAT.format(
            propertyName,
            "$propertyValue$padding"
        ).formatEmpty()
    )
}

private fun StringBuilder.property(
    propertyName: String,
    propertyClass: String,
    propertyValue: Any?,
    maxNameLength: Int,
    maxClassLength: Int,
    maxValueLength: Int,
): StringBuilder {
    val namePadding = " ".repeat(maxNameLength - propertyName.length)
    val classPadding = " ".repeat(maxClassLength - propertyClass.length)
    val valuePadding = " ".repeat(maxValueLength - propertyValue.toString().length)

    return appendLine(
        DeepLogStringBuilder.PROPERTY_FORMAT.format(
            "$propertyName$namePadding",
            "$propertyClass$classPadding",
            "$propertyValue$valuePadding"
        ).formatEmpty()
    )
}
