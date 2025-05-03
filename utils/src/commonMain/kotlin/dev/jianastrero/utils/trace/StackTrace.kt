package dev.jianastrero.utils.trace

@Throws(IllegalStateException::class)
internal expect fun getStackTrace(): List<String>

@Throws(IllegalStateException::class)
internal expect fun getCaller(): String
