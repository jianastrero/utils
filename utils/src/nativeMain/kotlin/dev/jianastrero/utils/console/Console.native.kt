package dev.jianastrero.utils.console

import dev.jianastrero.utils.log.LogLevel

actual fun println(message: String, tag: String, level: LogLevel) {
    val logMessage = "[${level.name}][$tag]: $message"
    kotlin.io.println(logMessage)
}
