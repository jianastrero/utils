package dev.jianastrero.utils.console

import dev.jianastrero.utils.log.LogLevel
import dev.jianastrero.utils.log.LogUtil
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class JvmConsoleTest : StringSpec({
    "println should format message with tag and level" {
        // Arrange
        val message = "Test message"
        val tag = "TestTag"
        val level = LogLevel.INFO
        val expectedLogMessage = "[${level.name}][$tag]: $message"

        // Redirect System.out to capture printed output
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        try {
            // Act
            println(message, tag, level)

            // Assert
            val printedOutput = outputStream.toString().trim()
            printedOutput shouldBe expectedLogMessage
        } finally {
            // Restore System.out
            System.setOut(originalOut)
        }
    }

    "println should use default tag and level when not provided" {
        // Arrange
        val message = "Test message"
        val defaultTag = LogUtil.tag
        val defaultLevel = LogLevel.DEBUG
        val expectedLogMessage = "[${defaultLevel.name}][$defaultTag]: $message"

        // Redirect System.out to capture printed output
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        try {
            // Act
            println(message)

            // Assert
            val printedOutput = outputStream.toString().trim()
            printedOutput shouldBe expectedLogMessage
        } finally {
            // Restore System.out
            System.setOut(originalOut)
        }
    }
})
