package dev.jianastrero.utils.log

import dev.jianastrero.utils.table.table

internal class DeepLogStringBuilder {
    var caller: String = ""
    var name: String = ""
    var className: String = ""
    var callStack: List<String> = emptyList()
    var value: Any? = null
    var properties: List<Triple<String, String, Any?>> = emptyList()
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

    val callStackStringList = callStack.mapIndexed { index, stack ->
        "${" ".repeat(index + 1)}↳ $stack "
    }

    return table(3) {
        header {
            cell("$name [$className]", 3)
        }

        if (properties.isEmpty()) {
            item {
                cell("value", 1)
                cell(valueString, 2)
            }
        } else {
            properties.forEach { (propertyName, propertyClass, propertyValue) ->
                item {
                    cell(propertyName)
                    cell(propertyClass)
                    cell(propertyValue.toString())
                }
            }
        }
    }
}
