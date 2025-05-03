package dev.jianastrero.utils.trace

import dev.jianastrero.utils.log.LogUtil
import kotlin.Throws

@Throws(IllegalStateException::class)
actual fun getStackTrace(): List<String> {
    val stackTrace = Thread.currentThread().stackTrace
    val importantStackTrace = mutableListOf<String>()

    var foundTop = false
    stackTrace.forEach { element ->
        if (!foundTop) {
            if (element.fileName == LogUtil.FILE_NAME && element.methodName == LogUtil.METHOD_NAME) foundTop = true
            else return@forEach
        }

        val className = element.className
        val methodName = element.methodName
        val fileName = element.fileName
        val lineNumber = element.lineNumber

        importantStackTrace.add("$fileName → $className → $methodName → $lineNumber")
    }

    return importantStackTrace
}

@Throws(exceptionClasses = [IllegalStateException::class])
actual fun getCaller(): String {
    val stackTrace = Thread.currentThread().stackTrace
    val getTop = stackTrace.indexOfFirst { element ->
        element.fileName == LogUtil.FILE_NAME && element.methodName == LogUtil.METHOD_NAME
    } + 1

    val caller = stackTrace.getOrNull(getTop)
        ?: return "Unknown File → Unknown Class → Unknown Function → Unknown Line Number"

    val className = caller.className
    val methodName = caller.methodName
    val fileName = caller.fileName
    val lineNumber = caller.lineNumber

    return "$fileName → $className → $methodName → $lineNumber"
}
