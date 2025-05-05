package dev.jianastrero.utils.log

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LogUtilTest : StringSpec({
    // Save original values to restore after tests
    val originalMinLogLevel = LogUtil.minLogLevel
    val originalTag = LogUtil.tag
    
    // Reset after each test
    afterTest {
        LogUtil.minLogLevel = originalMinLogLevel
        LogUtil.tag = originalTag
    }
    
    "LogUtil should have ERROR as default minLogLevel" {
        LogUtil.minLogLevel shouldBe LogLevel.ERROR
    }
    
    "LogUtil should have JIANDDEBUG as default tag" {
        LogUtil.tag shouldBe "JIANDDEBUG"
    }
    
    "LogUtil should allow changing minLogLevel" {
        // Arrange
        val newLogLevel = LogLevel.DEBUG
        
        // Act
        LogUtil.minLogLevel = newLogLevel
        
        // Assert
        LogUtil.minLogLevel shouldBe newLogLevel
    }
    
    "LogUtil should allow changing tag" {
        // Arrange
        val newTag = "TEST_TAG"
        
        // Act
        LogUtil.tag = newTag
        
        // Assert
        LogUtil.tag shouldBe newTag
    }
    
    "LogUtil.QUALIFIED_CLASS_NAME should be correct" {
        LogUtil.QUALIFIED_CLASS_NAME shouldBe "dev.jianastrero.utils.log.LogUtilKt"
    }
})
