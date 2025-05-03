package dev.jianastrero.utils

import dev.jianastrero.utils.log.LogLevel

expect fun println(message: String, tag: String = "JIANDDEBUG", level: LogLevel = LogLevel.DEBUG)
