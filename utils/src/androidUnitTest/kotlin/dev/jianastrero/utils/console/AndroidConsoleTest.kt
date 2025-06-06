package dev.jianastrero.utils.console

import android.util.Log
import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify

class AndroidConsoleTest : StringSpec({

    beforeTest {
        mockkStatic(Log::class)
    }

    afterTest {
        unmockkStatic(Log::class)
    }

    "println should call Log.e for ERROR level" {
        // Arrange
        val message = "Test error message"
        val tag = "TestTag"
        val level = LogLevel.ERROR
        val tagSlot = slot<String>()
        val messageSlot = slot<String>()
        every { Log.e(capture(tagSlot), capture(messageSlot)) } returns 0

        // Act
        println(message, tag, level)

        // Assert
        tagSlot.captured shouldBe tag
        messageSlot.captured shouldBe message
        verify { Log.e(any(), any()) }
    }

    "println should call Log.w for WARNING level" {
        // Arrange
        val message = "Test warning message"
        val tag = "TestTag"
        val level = LogLevel.WARNING
        val tagSlot = slot<String>()
        val messageSlot = slot<String>()
        every { Log.w(capture(tagSlot), capture(messageSlot)) } returns 0

        // Act
        println(message, tag, level)

        // Assert
        tagSlot.captured shouldBe tag
        messageSlot.captured shouldBe message
        verify { Log.w(any(), any<String>()) }
    }

    "println should call Log.i for INFO level" {
        // Arrange
        val message = "Test info message"
        val tag = "TestTag"
        val level = LogLevel.INFO
        val tagSlot = slot<String>()
        val messageSlot = slot<String>()
        every { Log.i(capture(tagSlot), capture(messageSlot)) } returns 0

        // Act
        println(message, tag, level)

        // Assert
        tagSlot.captured shouldBe tag
        messageSlot.captured shouldBe message
        verify { Log.i(any(), any()) }
    }

    "println should call Log.d for DEBUG level" {
        // Arrange
        val message = "Test debug message"
        val tag = "TestTag"
        val level = LogLevel.DEBUG
        val tagSlot = slot<String>()
        val messageSlot = slot<String>()
        every { Log.d(capture(tagSlot), capture(messageSlot)) } returns 0

        // Act
        println(message, tag, level)

        // Assert
        tagSlot.captured shouldBe tag
        messageSlot.captured shouldBe message
        verify { Log.d(any(), any()) }
    }

    "println should use default tag and level when not provided" {
        // Arrange
        val message = "Test message"
        val defaultTag = LogUtil.tag
        val tagSlot = slot<String>()
        val messageSlot = slot<String>()
        every { Log.d(capture(tagSlot), capture(messageSlot)) } returns 0

        // Act
        println(message)

        // Assert
        tagSlot.captured shouldBe defaultTag
        messageSlot.captured shouldBe message
        verify { Log.d(any(), any()) }
    }
})
