package dev.jianastrero.utils.trace

internal actual fun getStackTrace(): List<String> {
    error("getProperties is not supported on Native platform")
}

internal actual fun getCaller(): String {
    error("getProperties is not supported on Native platform")
}
