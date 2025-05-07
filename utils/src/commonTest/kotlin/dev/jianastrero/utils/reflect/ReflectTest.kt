package dev.jianastrero.utils.reflect

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ReflectTest : StringSpec({
    "getProperties function should exist" {
        // Arrange
        data class SimpleTestClass(val name: String = "Test")
        val testObject = SimpleTestClass()
        
        // Act
        val result = testObject.getProperties()
        
        // Assert
        result shouldNotBe null
        result.shouldBeInstanceOf<Map<String, Pair<String, Any?>>>()
    }
    
    "getProperties should return map with expected structure" {
        // Arrange
        data class TestClass(val testProperty: String = "TestValue")
        val testObject = TestClass()
        
        // Act
        val result = testObject.getProperties()
        
        // Assert
        // We can only make basic assertions in the common test
        // More detailed assertions are in platform-specific tests
        result.keys.contains("testProperty") shouldBe true
        
        val propertyValue = result["testProperty"]
        propertyValue shouldNotBe null
        propertyValue?.second shouldBe "TestValue"
    }
})
