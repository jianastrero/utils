package dev.jianastrero.utils.log

import dev.jianastrero.utils.println

inline fun <reified T> T.log(
    tag: String = "JIANDDEBUG",
    level: LogLevel = LogLevel.DEBUG,
): T {
    if (this is Throwable) {
        val message = "(${T::class.qualifiedName}) ${this.message ?: "No ERROR message"}"
        println(message = message, tag = tag, level = LogLevel.ERROR)
        println(stackTraceToString(), tag = tag, level = LogLevel.ERROR)
        return this
    }

    val logMessage = "(${T::class.qualifiedName}) $this"
    println(message = logMessage, tag = tag, level = level)
    return this
}

inline fun <reified T> T.logStackTrace() {

}
