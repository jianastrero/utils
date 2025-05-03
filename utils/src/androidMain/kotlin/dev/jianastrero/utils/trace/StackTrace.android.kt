package dev.jianastrero.utils.trace

import dev.jianastrero.utils.log.LogUtil

@Throws(exceptionClasses = [IllegalStateException::class])
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
    TODO("Not yet implemented")
}
