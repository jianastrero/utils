package dev.jianastrero.utils.log

import dev.jianastrero.utils.ext.splitLines
import dev.jianastrero.utils.table.TableBuilder
import dev.jianastrero.utils.table.TableTokens
import dev.jianastrero.utils.table.table

private const val CLASS_STRING = "kotlin.String"

/**
 * Builder class for creating detailed log messages.
 *
 * This class collects information about an object to be logged and provides
 * methods to set various properties for the log output.
 */
internal class DeepLogStringBuilder {
    /**
     * The caller information (typically a class and method name).
     */
    var caller: String = ""
        private set

    /**
     * The name of the object being logged.
     */
    var name: String = ""
        private set

    /**
     * The class name of the object being logged.
     */
    var className: String = ""
        private set

    /**
     * The call stack at the point of logging.
     */
    var callStack: List<String> = emptyList()
        private set

    /**
     * The actual object value being logged.
     */
    var value: Any? = null
        private set

    /**
     * The properties of the object being logged.
     * Each triple contains (property name, property class name, property value).
     */
    var properties: List<Triple<String, String, Any?>> = emptyList()
        private set

    /**
     * Sets the call stack information.
     *
     * @param callStack The list of call stack entries
     */
    fun callStack(callStack: List<String>) {
        this.callStack = callStack
    }

    /**
     * Sets the caller information.
     *
     * @param caller The caller information string
     */
    fun caller(caller: String) {
        this.caller = caller
    }

    /**
     * Sets the class name of the object being logged.
     *
     * @param className The class name
     */
    fun className(className: String) {
        this.className = className
    }

    /**
     * Sets the name of the object being logged.
     *
     * @param name The object name
     */
    fun name(name: String) {
        this.name = name
    }

    /**
     * Sets the properties of the object being logged.
     *
     * @param properties The list of property triples (name, class, value)
     */
    fun properties(properties: List<Triple<String, String, Any?>>) {
        this.properties = properties
    }

    /**
     * Sets the value of the object being logged.
     *
     * @param value The object value
     */
    fun value(value: Any?) {
        this.value = value
    }
}

/**
 * Creates a formatted log message using the provided configuration.
 *
 * This function uses a builder pattern to collect information about the object
 * being logged and formats it into a structured table representation.
 *
 * @param block The configuration block for the log builder
 * @return A formatted string representation of the log
 * @throws Nothing This function does not throw any exceptions
 *
 * @see DeepLogStringBuilder
 *
 * @example
 * ```
 * val logMessage = deepLog {
 *     name("user")
 *     className("com.example.User")
 *     caller("MainActivity.onCreate")
 *     value(user)
 *     properties(listOf(Triple("name", "kotlin.String", "John")))
 *     callStack(listOf("MainActivity.onCreate", "Application.onCreate"))
 * }
 * ```
 */
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

    val value = builder.value
    val valueString = if (properties.isEmpty()) builder.value?.toString().orEmpty() else ""

    val callStackStringList = callStack.mapIndexed { index, stack ->
        "${" ".repeat(index + 1)}↳ $stack "
    }

    val innerTable = table(3) {
        !"$name [$className]"

        when {
            value is Array<*> -> {
                value.forEachIndexed { index, item ->
                    collection(name = name, item = item, index = index)
                }
            }
            value is Iterable<*> -> {
                value.forEachIndexed { index, item ->
                    collection(name = name, item = item, index = index)
                }
            }
            value is Map<*, *> -> {
                value.forEach { (key, item) ->
                    collection(name = name, item = item, index = key)
                }
            }
            properties.isEmpty() -> {
                +arrayOf(
                    "value" * 1,
                    valueString * 2
                )
            }
            else -> {
                properties.forEach { (propertyName, propertyClass, propertyValue) ->
                    +arrayOf(
                        propertyName,
                        propertyClass,
                        propertyValue.toString()
                    )
                }
            }
        }
    }

    val outerTable = table(1, TableTokens.Thick) {
        !builder.caller
        innerTable.splitLines().forEach { !it }
        callStackStringList.forEach { +it }
    }

    return outerTable
}

/**
 * Adds a collection item to a table.
 *
 * This helper function formats a collection item for display in a table.
 *
 * @param name The name of the collection
 * @param item The item from the collection
 * @param index The index or key of the item
 * @throws Nothing This function does not throw any exceptions
 */
private fun TableBuilder.collection(
    name: String,
    item: Any?,
    index: Any?,
) {
    +arrayOf(
        "$name[$index]",
        item.toString(),
        (item?.let { it::class.qualifiedName } ?: "Unknown Class")
    )
}
