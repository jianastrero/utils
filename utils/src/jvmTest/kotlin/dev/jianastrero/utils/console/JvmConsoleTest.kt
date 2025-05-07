package dev.jianastrero.utils.console

import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.verify
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class JvmConsoleTest : StringSpec({
    lateinit var originalOut: PrintStream
    lateinit var outputStream: ByteArrayOutputStream

    beforeTest {
        originalOut = System.out
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    afterTest {
        System.setOut(originalOut)
    }

    "println should format message with tag and level" {
        // Arrange
        val message = "Test message"
        val tag = "TestTag"
        val level = LogLevel.INFO
        val expectedLogMessage = "[${level.name}][$tag]: $message"

        // Act
        println(message, tag, level)

        // Assert
        val printedOutput = outputStream.toString().trim()
        printedOutput shouldBe expectedLogMessage
    }

    "println should use default tag and level when not provided" {
        // Arrange
        val message = "Test message"
        val defaultTag = LogUtil.tag
        val defaultLevel = LogLevel.DEBUG
        val expectedLogMessage = "[${defaultLevel.name}][$defaultTag]: $message"

        // Act
        println(message)

        // Assert
        val printedOutput = outputStream.toString().trim()
        printedOutput shouldBe expectedLogMessage
    }
})
