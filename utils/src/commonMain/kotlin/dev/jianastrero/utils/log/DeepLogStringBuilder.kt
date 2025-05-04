package dev.jianastrero.utils.log

import dev.jianastrero.utils.ext.splitLines
import dev.jianastrero.utils.table.TableTokens
import dev.jianastrero.utils.table.table
import dev.jianastrero.utils.trace.getCaller

internal class DeepLogStringBuilder {
    var caller: String = ""
        private set

    var name: String = ""
        private set

    var className: String = ""
        private set

    var callStack: List<String> = emptyList()
        private set

    var value: Any? = null
        private set

    var properties: List<Triple<String, String, Any?>> = emptyList()
        private set

    fun caller(caller: String) {
        this.caller = caller
    }

    fun name(name: String) {
        this.name = name
    }

    fun className(className: String) {
        this.className = className
    }

    fun callStack(callStack: List<String>) {
        this.callStack = callStack
    }

    fun value(value: Any?) {
        this.value = value
    }

    fun properties(properties: List<Triple<String, String, Any?>>) {
        this.properties = properties
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

    val callStackStringList = callStack.mapIndexed { index, stack ->
        "${" ".repeat(index + 1)}↳ $stack "
    }

    val innerTable = table(3) {
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

    val outerTable = table(1, TableTokens.Thick) {
        header {
            cell(builder.caller)
        }
        innerTable.splitLines().forEach { line ->
            header {
                cell(line)
            }
        }
        callStackStringList.forEach {
            item {
                cell(it)
            }
        }
    }

    return outerTable
}
