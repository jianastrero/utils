package dev.jianastrero.utils.trace

@Throws(IllegalStateException::class)
actual fun getStackTrace(): List<String> {
    error("getStackTrace is not supported on Native platform")
}

@Throws(exceptionClasses = [IllegalStateException::class])
actual fun getCaller(): String {
    error("getCaller is not supported on Native platform")
}
