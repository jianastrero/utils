package dev.jianastrero.utils.log

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain

class DeepLogStringBuilderTest : StringSpec({
    "deepLog should include name and class name" {
        // Arrange
        val name = "testObject"
        val className = "TestClass"
        
        // Act
        val result = deepLog {
            name(name)
            className(className)
        }
        
        // Assert
        result shouldContain name
        result shouldContain className
    }
    
    "deepLog should include caller information" {
        // Arrange
        val caller = "TestCaller.testMethod"
        
        // Act
        val result = deepLog {
            caller(caller)
        }
        
        // Assert
        result shouldContain caller
    }
    
    "deepLog should include value information" {
        // Arrange
        val testValue = "Test Value"
        
        // Act
        val result = deepLog {
            value(testValue)
        }
        
        // Assert
        result shouldContain testValue
    }
    
    "deepLog should include properties" {
        // Arrange
        val propertyName = "testProperty"
        val propertyClass = "kotlin.String"
        val propertyValue = "Test Property Value"
        val properties = listOf(Triple(propertyName, propertyClass, propertyValue))
        
        // Act
        val result = deepLog {
            properties(properties)
        }
        
        // Assert
        result shouldContain propertyName
        result shouldContain propertyClass
        result shouldContain propertyValue
    }
    
    "deepLog should include call stack" {
        // Arrange
        val callStackEntry = "TestClass.testMethod"
        val callStack = listOf(callStackEntry)
        
        // Act
        val result = deepLog {
            callStack(callStack)
        }
        
        // Assert
        result shouldContain callStackEntry
    }
    
    "deepLog should handle array values" {
        // Arrange
        val arrayValue = arrayOf("item1", "item2")
        
        // Act
        val result = deepLog {
            name("testArray")
            value(arrayValue)
        }
        
        // Assert
        result shouldContain "testArray[0]"
        result shouldContain "item1"
        result shouldContain "testArray[1]"
        result shouldContain "item2"
    }
    
    "deepLog should handle list values" {
        // Arrange
        val listValue = listOf("item1", "item2")
        
        // Act
        val result = deepLog {
            name("testList")
            value(listValue)
        }
        
        // Assert
        result shouldContain "testList[0]"
        result shouldContain "item1"
        result shouldContain "testList[1]"
        result shouldContain "item2"
    }
    
    "deepLog should handle map values" {
        // Arrange
        val mapValue = mapOf("key1" to "value1", "key2" to "value2")
        
        // Act
        val result = deepLog {
            name("testMap")
            value(mapValue)
        }
        
        // Assert
        result shouldContain "testMap[key1]"
        result shouldContain "value1"
        result shouldContain "testMap[key2]"
        result shouldContain "value2"
    }
})
