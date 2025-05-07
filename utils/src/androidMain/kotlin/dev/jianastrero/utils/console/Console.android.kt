package dev.jianastrero.utils.console

import android.util.Log
import dev.jianastrero.utils.log.LogLevel

actual fun println(message: String, tag: String, level: LogLevel) {
    when (level) {
        LogLevel.ERROR -> Log.e(tag, message)
        LogLevel.WARNING -> Log.w(tag, message)
        LogLevel.INFO -> Log.i(tag, message)
        LogLevel.DEBUG -> Log.d(tag, message)
    }
}
