package dev.jianastrero.utils.console

import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil

/**
 * Prints a message to the standard output with optional tag and log level.
 *
 * This is a multiplatform function that provides consistent logging across different platforms.
 * Each platform implements this function according to its specific logging capabilities.
 *
 * @param message The message to print
 * @param tag The tag to identify the source of the log (defaults to [LogUtil.tag])
 * @param level The severity level of the log (defaults to [LogLevel.DEBUG])
 *
 * @see LogLevel
 * @see LogUtil
 *
 * @throws Nothing This function does not throw any exceptions
 */
expect fun println(message: String, tag: String = LogUtil.tag, level: LogLevel = LogLevel.DEBUG)
