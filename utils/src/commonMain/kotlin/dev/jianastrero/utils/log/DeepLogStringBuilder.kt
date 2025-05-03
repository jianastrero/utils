package dev.jianastrero.utils.log

import dev.jianastrero.utils.format.format
import dev.jianastrero.utils.format.formatEmpty

internal class DeepLogStringBuilder {
    var caller: String = ""
    var name: String = ""
    var className: String = ""
    var callStack: List<String> = emptyList()
    var value: Any? = null
    var properties: List<Triple<String, String, Any?>> = emptyList()

    companion object {
        const val HEADER_FORMAT = " %s [%s]%s "
        const val PROPERTY_FORMAT = " %s │ %s │ %s "
        const val VALUE_FORMAT = " %s │ %s "

        const val BOX_TOP        = "┏%s┓"
        const val BOX_MID        = "┃%s┃"
        const val BOX_BOTTOM     = "┗%s┛"
        const val BOX_HR         = "┣%s┫"
        const val BOX_HORIZONTAL = "━"

        const val THIN_BOX_TOP        = "┌%s┐"
        const val THIN_BOX_MID        = "│%s│"
        const val THIN_BOX_BOTTOM     = "└%s┘"
        const val THIN_HR             = "├%s┤"
        const val THIN_BOX_HORIZONTAL = "─"
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

    val callStackStringList = callStack.mapIndexed { index, stack ->
        "${" ".repeat(index + 1)}↳ $stack "
    }
    val maxCallStackLength = callStackStringList.maxOfOrNull { it.length } ?: 0

    val tableLength = maxOf(headerLength, propertyLength, valueLength)
    val boxLength = maxOf(tableLength, maxCallStackLength, builder.caller.length)

    val headerText = header(name = name, className = className, formatLength = headerFormatLength, tableLength = tableLength)

    return StringBuilder()
        .appendLine(boxTop(boxLength))
        .appendLine(boxMid(value = builder.caller, length = boxLength))
        .appendLine(boxMid(value = thinBoxTop(tableLength), length = boxLength))
        .appendLine(boxMid(value = thinBoxMid(value = headerText, length = tableLength), length = boxLength))
        .appendLine(boxMid(value = thinHR(tableLength), length = boxLength))
        .apply {
            if (properties.isEmpty()) {
                val valueText = value(
                    propertyValue = valueString,
                    tableLength = tableLength,
                    formatLength = valueFormatLength
                )
                appendLine(
                    boxMid(value = thinBoxMid(value = valueText, length = tableLength), length = boxLength))
            } else {
                properties.forEach { (propertyName, propertyClass, propertyValue) ->
                    val propertyText = property(
                        propertyName = propertyName,
                        propertyClass = propertyClass,
                        propertyValue = propertyValue,
                        maxNameLength = maxNameLength,
                        maxClassLength = maxClassLength,
                        maxValueLength = maxValueLength
                    )
                    appendLine(
                        boxMid(value = thinBoxMid(value = propertyText, length = tableLength), length = boxLength)
                    )
                }
            }
        }
        .appendLine(boxMid(value = thinBoxBottom(length = tableLength), length = boxLength))
        .appendLine(boxHR(boxLength))
        .apply {
            callStackStringList.forEach { appendLine(boxMid(it, boxLength)) }
        }
        .appendLine(boxBottom(boxLength))
        .toString()
        .trim()
}

private fun boxTop(length: Int): String {
    return DeepLogStringBuilder.BOX_TOP.format(DeepLogStringBuilder.BOX_HORIZONTAL.repeat(length))
}

private fun boxBottom(length: Int): String {
    return DeepLogStringBuilder.BOX_BOTTOM.format(DeepLogStringBuilder.BOX_HORIZONTAL.repeat(length))
}

private fun boxMid(value: String, length: Int): String {
    val padding = " ".repeat(length - value.length)
    return DeepLogStringBuilder.BOX_MID.format("$value$padding")
}

private fun boxHR(length: Int): String {
    return DeepLogStringBuilder.BOX_HR.format(DeepLogStringBuilder.BOX_HORIZONTAL.repeat(length))
}

private fun thinBoxTop(length: Int): String {
    return DeepLogStringBuilder.THIN_BOX_TOP.format(DeepLogStringBuilder.THIN_BOX_HORIZONTAL.repeat(length))
}

private fun thinBoxBottom(length: Int): String {
    return DeepLogStringBuilder.THIN_BOX_BOTTOM.format(DeepLogStringBuilder.THIN_BOX_HORIZONTAL.repeat(length))
}

private fun thinBoxMid(
    value: String,
    length: Int,
): String {
    val padding = " ".repeat(length - value.length)
    return DeepLogStringBuilder.THIN_BOX_MID.format("$value$padding")
}

private fun thinHR(length: Int): String {
    return DeepLogStringBuilder.THIN_HR.format(DeepLogStringBuilder.THIN_BOX_HORIZONTAL.repeat(length))
}

private fun header(
    name: String,
    className: String,
    formatLength: Int,
    tableLength: Int,
): String {
    val padding = " ".repeat(tableLength - name.length - className.length - formatLength)

    return DeepLogStringBuilder.HEADER_FORMAT.format(
        name,
        className,
        padding
    )
}

private fun value(
    propertyValue: String,
    tableLength: Int,
    formatLength: Int,
): String {
    val padding = " ".repeat(tableLength - "value".length - propertyValue.length - formatLength)

    return DeepLogStringBuilder.VALUE_FORMAT.format(
        "value",
        "$propertyValue$padding"
    )
}

private fun property(
    propertyName: String,
    propertyClass: String,
    propertyValue: Any?,
    maxNameLength: Int,
    maxClassLength: Int,
    maxValueLength: Int,
): String {
    val namePadding = " ".repeat(maxNameLength - propertyName.length)
    val classPadding = " ".repeat(maxClassLength - propertyClass.length)
    val valuePadding = " ".repeat(maxValueLength - propertyValue.toString().length)

    return DeepLogStringBuilder.PROPERTY_FORMAT.format(
        "$propertyName$namePadding",
        "$propertyClass$classPadding",
        "$propertyValue$valuePadding"
    )
}
