package dev.jianastrero.utils.trace

import dev.jianastrero.utils.log.LogUtil

@Throws(IllegalStateException::class)
actual fun getStackTrace(): List<String> {
    val stackTrace = Thread.currentThread().stackTrace
    val importantStackTrace = mutableListOf<String>()
    val top = stackTrace.indexOfLast { it.className == LogUtil.QUALIFIED_CLASS_NAME } + 1

    stackTrace.forEachIndexed { index, element ->
        if (index < top) {
            return@forEachIndexed
        }

        val className = element.className
        val methodName = element.methodName
        val fileName = element.fileName
        val lineNumber = element.lineNumber.coerceIn(0, Int.MAX_VALUE)

        importantStackTrace.add("$fileName → $className → $methodName → line $lineNumber")
    }

    return importantStackTrace
}

@Throws(exceptionClasses = [IllegalStateException::class])
actual fun getCaller(): String {
    val stackTrace = Thread.currentThread().stackTrace
    val top = stackTrace.indexOfLast { it.className == LogUtil.QUALIFIED_CLASS_NAME } + 1

    val caller = stackTrace.getOrNull(top)
        ?: return "Unknown File → Unknown Class → Unknown Function → Unknown Line Number"

    val fileName = caller.fileName
    val className = caller.className
    val methodName = caller.methodName
    val lineNumber = caller.lineNumber.coerceIn(0, Int.MAX_VALUE)

    return "$fileName → $className → $methodName → line $lineNumber"
}
