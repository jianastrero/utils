package dev.jianastrero.utils.log

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LogExtensionsTest : StringSpec({
    // Save original values to restore after tests
    val originalMinLogLevel = LogUtil.minLogLevel
    val originalTag = LogUtil.tag
    
    // Reset after each test
    afterTest {
        LogUtil.minLogLevel = originalMinLogLevel
        LogUtil.tag = originalTag
    }
    
    "log() should return the original object" {
        // Arrange
        val testObject = "Test String"
        
        // Act
        val result = testObject.log()
        
        // Assert
        result shouldBe testObject
    }
    
    "log() should return null if the object is null" {
        // Arrange
        val testObject: String? = null
        
        // Act
        val result = testObject.log()
        
        // Assert
        result shouldBe null
    }
    
    "log() should not log if level is below minLogLevel" {
        // Arrange
        val testObject = "Test String"
        LogUtil.minLogLevel = LogLevel.ERROR
        
        // Act & Assert (no way to assert output, but at least we verify it doesn't crash)
        testObject.log(level = LogLevel.DEBUG) shouldBe testObject
        testObject.log(level = LogLevel.INFO) shouldBe testObject
        testObject.log(level = LogLevel.WARNING) shouldBe testObject
    }
    
    "logDeep() should return the original object" {
        // Arrange
        val testObject = "Test String"
        
        // Act
        val result = testObject.logDeep("testObject")
        
        // Assert
        result shouldBe testObject
    }
    
    "logDeep() should return null if the object is null" {
        // Arrange
        val testObject: String? = null
        
        // Act
        val result = testObject.logDeep("testObject")
        
        // Assert
        result shouldBe null
    }
    
    "logDeep() should not log if level is below minLogLevel" {
        // Arrange
        val testObject = "Test String"
        LogUtil.minLogLevel = LogLevel.ERROR
        
        // Act & Assert (no way to assert output, but at least we verify it doesn't crash)
        testObject.logDeep("testObject", level = LogLevel.DEBUG) shouldBe testObject
        testObject.logDeep("testObject", level = LogLevel.INFO) shouldBe testObject
        testObject.logDeep("testObject", level = LogLevel.WARNING) shouldBe testObject
    }
    
    "log() should handle Throwable objects" {
        // Arrange
        val testException = RuntimeException("Test exception")
        
        // Act & Assert (no way to assert output, but at least we verify it doesn't crash)
        testException.log() shouldBe testException
    }
    
    "logDeep() should handle Throwable objects" {
        // Arrange
        val testException = RuntimeException("Test exception")
        
        // Act & Assert (no way to assert output, but at least we verify it doesn't crash)
        testException.logDeep("testException") shouldBe testException
    }
})
