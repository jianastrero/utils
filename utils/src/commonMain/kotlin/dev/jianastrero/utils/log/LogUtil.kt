package dev.jianastrero.utils.log

import dev.jianastrero.utils.println
import dev.jianastrero.utils.reflect.getProperties
import dev.jianastrero.utils.trace.getCaller
import dev.jianastrero.utils.trace.getStackTrace

/**
 * Utility object for logging configuration.
 *
 * This object provides global configuration for the logging system.
 */
data object LogUtil {
    private const val DEFAULT_TAG = "JIANDDEBUG"

    internal const val QUALIFIED_CLASS_NAME = "dev.jianastrero.utils.log.LogUtilKt"

    /**
     * The minimum log level that will be displayed.
     * Messages with a level below this will be ignored.
     */
    var minLogLevel: LogLevel = LogLevel.ERROR

    /**
     * The default tag used for logging messages.
     */
    var tag: String = DEFAULT_TAG
}

/**
 * Gets the call stack for the current execution context.
 *
 * @param name The name to use in error logs if getting the call stack fails
 * @param level The log level to use for error logs
 * @return A list of stack trace elements as strings, or null if an error occurred
 * @throws Nothing This function catches all exceptions internally
 */
private inline fun <reified T> T.getCallStack(
    name: String,
    level: LogLevel
) = runCatching { getStackTrace() }
    .getOrElse { exception ->
        exception.log(name, level)
        null
    }

/**
 * Gets a list of properties for the current object.
 *
 * @param name The name to use in error logs if getting properties fails
 * @param level The log level to use for error logs
 * @return A list of property triples (name, class, value), or null if an error occurred
 * @throws Nothing This function catches all exceptions internally
 */
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

/**
 * Logs the current object with basic information.
 *
 * This extension function can be used on any object to log its basic information.
 * If the object is null, it logs a null message.
 * If the object is a Throwable, it logs the error message and stack trace.
 *
 * @param tag The tag to identify the source of the log (defaults to [LogUtil.tag])
 * @param level The severity level of the log (defaults to [LogLevel.DEBUG])
 * @return The original object (this), allowing for method chaining
 * @throws Nothing This function catches all exceptions internally
 *
 * @see LogLevel
 * @see LogUtil
 *
 * @example
 * ```
 * val user = User("John", 25)
 * user.log() // Logs: "(com.example.User) User(name=John, age=25)"
 * ```
 */
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

/**
 * Logs detailed information about the current object.
 *
 * This extension function provides a more comprehensive log output than [log],
 * including property values, class information, and call stack.
 * For collections (Array, Iterable, Map), it logs each element individually.
 *
 * @param name A name to identify this object in the log
 * @param tag The tag to identify the source of the log (defaults to [LogUtil.tag])
 * @param level The severity level of the log (defaults to [LogLevel.DEBUG])
 * @return The original object (this), allowing for method chaining
 * @throws Nothing This function catches all exceptions internally
 *
 * @see log
 * @see LogLevel
 * @see LogUtil
 *
 * @example
 * ```
 * val user = User("John", 25)
 * user.logDeep("currentUser") // Logs detailed information about the user object
 * ```
 */
fun <T> T.logDeep(name: String, tag: String = LogUtil.tag, level: LogLevel = LogLevel.DEBUG): T {
    if (level.level < LogUtil.minLogLevel.level) return this
    if (this == null) {
        "$name is NULL".log(tag = tag, level = level)
        return this
    }

    val kClass = this::class
    val caller = getCaller()
    val className = kClass.qualifiedName ?: "Unknown Class"
    val propertiesList = when (this) {
        is Array<*>,
        is Iterable<*>,
        is Map<*, *> -> emptyList()
        else -> getPropertyList(name = name, level = level)
    }
    val callStack = getCallStack(name = name, level = level)

    val logMessage = deepLog {
        name(name)
        className(className)
        caller(caller)
        value(this@logDeep)
        properties(propertiesList ?: emptyList())
        callStack(callStack ?: emptyList())
    }

    println(
        message = "\n$logMessage",
        tag = tag,
        level = level,
    )

    if (this is Throwable) {
        printStackTrace()
    }

    return this
}
