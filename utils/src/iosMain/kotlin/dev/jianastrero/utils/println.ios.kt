package dev.jianastrero.utils

import dev.jianastrero.utils.log.LogLevel
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ptr
import platform.darwin.OS_LOG_DEFAULT
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_FAULT
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.__dso_handle
import platform.darwin._os_log_internal

private const val LOG_FORMAT = "%s: %s"

actual fun println(message: String, tag: String, level: LogLevel) {
    when (level) {
        LogLevel.ERROR -> logError(tag, message)
        LogLevel.WARNING -> logWarning(tag, message)
        LogLevel.INFO -> logInfo(tag, message)
        LogLevel.DEBUG -> logDebug(tag, message)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun logDebug(tag: String, message: String) {
    _os_log_internal(
        dso = __dso_handle.ptr,
        log = OS_LOG_DEFAULT,
        type = OS_LOG_TYPE_DEBUG,
        message = LOG_FORMAT,
        tag,
        message
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun logInfo(tag: String, message: String) {
    _os_log_internal(
        dso = __dso_handle.ptr,
        log = OS_LOG_DEFAULT,
        type = OS_LOG_TYPE_INFO,
        message = LOG_FORMAT,
        tag,
        message
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun logError(tag: String, message: String) {
    _os_log_internal(
        dso = __dso_handle.ptr,
        log = OS_LOG_DEFAULT,
        type = OS_LOG_TYPE_ERROR,
        message = LOG_FORMAT,
        tag,
        message
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun logWarning(tag: String, message: String) {
    _os_log_internal(
        dso = __dso_handle.ptr,
        log = OS_LOG_DEFAULT,
        type = OS_LOG_TYPE_FAULT,
        message = LOG_FORMAT,
        tag,
        message
    )
}
