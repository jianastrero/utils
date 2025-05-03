package dev.jianastrero.utils

import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil

expect fun println(message: String, tag: String = LogUtil.tag, level: LogLevel = LogLevel.DEBUG)
