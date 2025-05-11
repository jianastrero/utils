package dev.jianastrero.utils.console

actual fun println(message: String, tag: String, level: dev.jianastrero.utils.log.LogLevel) {
    val logMessage = "[${level.name}][$tag]: $message"
    kotlin.io.println(logMessage)
}
