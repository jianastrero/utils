package dev.jianastrero.utils.format

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FormatTest : StringSpec({
    "format should replace %s with string argument" {
        // Arrange
        val template = "Hello, %s!"
        val arg = "World"

        // Act
        val result = template.format(arg)

        // Assert
        result shouldBe "Hello, World!"
    }

    "format should replace multiple format tokens in order" {
        // Arrange
        val template = "Hello, %s! Today is %s."
        val arg1 = "World"
        val arg2 = "Monday"

        // Act
        val result = template.format(arg1, arg2)

        // Assert
        result shouldBe "Hello, World! Today is Monday."
    }

    "format should handle different format tokens" {
        // Arrange
        val template = "Number: %d, Float: %f, Char: %c, Boolean: %b"
        val arg1 = 42
        val arg2 = 3.14
        val arg3 = 'A'
        val arg4 = true

        // Act
        val result = template.format(arg1, arg2, arg3, arg4)

        // Assert
        result shouldBe "Number: 42, Float: 3.14, Char: A, Boolean: true"
    }

    "format should handle hex, octal, and scientific notation tokens" {
        // Arrange
        val template = "Hex: %x, Octal: %o, Scientific: %e"
        val arg1 = 255
        val arg2 = 64
        val arg3 = 1000.0

        // Act
        val result = template.format(arg1, arg2, arg3)

        // Assert
        result shouldBe "Hex: 255, Octal: 64, Scientific: 1000.0"
    }

    "format should handle general, hex float, and hashcode tokens" {
        // Arrange
        val template = "General: %g, Hex Float: %a, Hashcode: %h"
        val arg1 = 123.456
        val arg2 = 0.5
        val obj = "Test"

        // Act
        val result = template.format(arg1, arg2, obj)

        // Assert
        result shouldBe "General: 123.456, Hex Float: 0.5, Hashcode: Test"
    }

    "formatEmpty should remove unused format tokens" {
        // Arrange
        val template = "Hello, %s! Number: %d, Float: %f"

        // Act
        val result = template.formatEmpty()

        // Assert
        result shouldBe "Hello, ! Number: , Float: "
    }

    "format should handle newline token" {
        // Arrange
        val template = "Line 1%nLine 2"

        // Act
        val result = template.formatEmpty()

        // Assert
        result shouldBe "Line 1\nLine 2"
    }

    "format should handle percent token" {
        // Arrange
        val template = "Percentage: 100%%"

        // Act
        val result = template.formatEmpty()

        // Assert
        result shouldBe "Percentage: 100%"
    }

    "format should handle date/time token" {
        // Arrange
        val template = "Date: %t"
        val dateString = "2023-01-01"

        // Act
        val result = template.format(dateString)

        // Assert
        result shouldBe "Date: $dateString"
    }

    "format should stop replacing when no more arguments are available" {
        // Arrange
        val template = "Hello, %s! Number: %d"
        val arg = "World"

        // Act
        val result = template.format(arg)

        // Assert
        result shouldBe "Hello, World! Number: "
    }
})
