package dev.jianastrero.utils.reflect

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AndroidReflectTest : StringSpec({
    "getProperties should return empty map for object with no properties" {
        // Arrange
        class EmptyClass

        // Act
        val result = EmptyClass().getProperties()

        // Assert
        result shouldBe emptyMap()
    }

    "getProperties should return correct properties for simple data class" {
        // Arrange
        data class TestClass(val name: String, val age: Int)

        val testObject = TestClass("John", 30)

        // Act
        val result = testObject.getProperties()

        // Assert
        result.size shouldBe 2
        result["name"]?.second shouldBe "John"
        result["age"]?.second shouldBe 30
        result["name"]?.first shouldBe "kotlin.String"
        result["age"]?.first shouldBe "kotlin.Int"
    }

    "getProperties should handle null property values" {
        // Arrange
        data class TestClass(val name: String?, val age: Int?)

        val testObject = TestClass(null, null)

        // Act
        val result = testObject.getProperties()

        // Assert
        result.size shouldBe 2
        result["name"]?.second shouldBe null
        result["age"]?.second shouldBe null
    }

    "getProperties should handle nested objects" {
        // Arrange
        data class Address(val street: String, val city: String)
        data class Person(val name: String, val address: Address)

        val address = Address("123 Main St", "New York")
        val person = Person("John", address)

        // Act
        val result = person.getProperties()

        // Assert
        result.size shouldBe 2
        result["name"]?.second shouldBe "John"
        result["address"]?.second shouldBe address
        // The implementation returns "Unknown Class" for local class qualified names
        result["address"]?.first shouldBe "Unknown Class"
    }
})
