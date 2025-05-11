package dev.jianastrero.utils.trace

internal expect fun getStackTrace(): List<String>
internal expect fun getCaller(): String
