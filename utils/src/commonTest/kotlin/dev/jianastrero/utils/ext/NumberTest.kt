package dev.jianastrero.utils.ext

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow

class NumberTest : StringSpec({
    "orZero should return 0 for null Byte" {
        // Arrange
        val nullByte: Byte? = null

        // Act
        val result = nullByte.orZero()

        // Assert
        result shouldBe 0.toByte()
    }

    "orZero should return original value for non-null Byte" {
        // Arrange
        val byte: Byte? = 5

        // Act
        val result = byte.orZero()

        // Assert
        result shouldBe 5.toByte()
    }

    "orZero should return 0 for null Short" {
        // Arrange
        val nullShort: Short? = null

        // Act
        val result = nullShort.orZero()

        // Assert
        result shouldBe 0.toShort()
    }

    "orZero should return original value for non-null Short" {
        // Arrange
        val short: Short? = 10

        // Act
        val result = short.orZero()

        // Assert
        result shouldBe 10.toShort()
    }

    "orZero should return 0 for null Int" {
        // Arrange
        val nullInt: Int? = null

        // Act
        val result = nullInt.orZero()

        // Assert
        result shouldBe 0
    }

    "orZero should return original value for non-null Int" {
        // Arrange
        val int: Int? = 42

        // Act
        val result = int.orZero()

        // Assert
        result shouldBe 42
    }

    "orZero should return 0 for null Long" {
        // Arrange
        val nullLong: Long? = null

        // Act
        val result = nullLong.orZero()

        // Assert
        result shouldBe 0L
    }

    "orZero should return original value for non-null Long" {
        // Arrange
        val long: Long? = 1000L

        // Act
        val result = long.orZero()

        // Assert
        result shouldBe 1000L
    }

    "orZero should return 0 for null Float" {
        // Arrange
        val nullFloat: Float? = null

        // Act
        val result = nullFloat.orZero()

        // Assert
        result shouldBe 0f
    }

    "orZero should return original value for non-null Float" {
        // Arrange
        val float: Float? = 3.14f

        // Act
        val result = float.orZero()

        // Assert
        result shouldBe 3.14f
    }

    "orZero should return 0 for null Double" {
        // Arrange
        val nullDouble: Double? = null

        // Act
        val result = nullDouble.orZero()

        // Assert
        result shouldBe 0.0
    }

    "orZero should return original value for non-null Double" {
        // Arrange
        val double: Double? = 2.71828

        // Act
        val result = double.orZero()

        // Assert
        result shouldBe 2.71828
    }

    "orZero should throw error for unsupported Number type" {
        // Arrange
        val customNumber = object : Number() {
            override fun toByte(): Byte = 0
            override fun toShort(): Short = 0
            override fun toInt(): Int = 0
            override fun toLong(): Long = 0L
            override fun toFloat(): Float = 0f
            override fun toDouble(): Double = 0.0
        }

        // Act & Assert
        shouldThrow<IllegalStateException> {
            customNumber.orZero()
        }
    }
})
