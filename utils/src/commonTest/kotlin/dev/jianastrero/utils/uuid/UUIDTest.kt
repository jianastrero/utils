package dev.jianastrero.utils.uuid

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Constraints.Companion.iterations

class UUIDTest : StringSpec({
    "UUID generation should produce a valid UUID" {
        // Arrange
        val uuid = generateUUID()

        // Act
        val isValidUUID = uuid.matches(Regex("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\$"))

        // Assert
        isValidUUID shouldBe true
    }

    "UUID generation without dashes should produce a valid UUID" {
        // Arrange
        val uuid = generateUUID(dashed = false)

        // Act
        val isValidUUID = uuid.matches(Regex("^[0-9a-f]{32}\$"))

        // Assert
        isValidUUID shouldBe true
    }

    "UUID generation should produce 1 million unique UUIDs" {
        // Arrange
        val uuidSet = mutableSetOf<String>()
        val iterations = 1_000_000

        // Act
        repeat(iterations) {
            uuidSet.add(generateUUID())
        }

        // Assert
        uuidSet.size shouldBe iterations
    }
})
