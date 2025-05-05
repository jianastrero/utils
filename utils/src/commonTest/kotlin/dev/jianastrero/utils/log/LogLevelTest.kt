package dev.jianastrero.utils.log

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LogLevelTest : StringSpec({
    "LogLevel.DEBUG should have level 0" {
        LogLevel.DEBUG.level shouldBe 0
    }
    
    "LogLevel.INFO should have level 1" {
        LogLevel.INFO.level shouldBe 1
    }
    
    "LogLevel.WARNING should have level 2" {
        LogLevel.WARNING.level shouldBe 2
    }
    
    "LogLevel.ERROR should have level 3" {
        LogLevel.ERROR.level shouldBe 3
    }
    
    "LogLevel values should be in ascending order of severity" {
        val levels = LogLevel.values()
        for (i in 0 until levels.size - 1) {
            levels[i].level shouldBe i
            levels[i].level shouldBe (levels[i + 1].level - 1)
        }
    }
    
    "LogLevel comparison should work correctly" {
        (LogLevel.DEBUG.level < LogLevel.INFO.level) shouldBe true
        (LogLevel.INFO.level < LogLevel.WARNING.level) shouldBe true
        (LogLevel.WARNING.level < LogLevel.ERROR.level) shouldBe true
    }
})
