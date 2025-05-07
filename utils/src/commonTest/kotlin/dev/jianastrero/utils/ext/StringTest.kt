package dev.jianastrero.utils.ext

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StringTest : StringSpec({
    // padAround tests
    "padAround should add equal padding on both sides when possible" {
        // Arrange
        val input = "test"
        val length = 10
        
        // Act
        val result = input.padAround(length)
        
        // Assert
        result shouldBe "   test   "
        result.length shouldBe length
    }
    
    "padAround should add extra padding on right when padding is odd" {
        // Arrange
        val input = "test"
        val length = 9
        
        // Act
        val result = input.padAround(length)
        
        // Assert
        result shouldBe "  test   "
        result.length shouldBe length
    }
    
    "padAround should use specified padding character" {
        // Arrange
        val input = "test"
        val length = 8
        val padChar = '*'
        
        // Act
        val result = input.padAround(length, padChar)
        
        // Assert
        result shouldBe "**test**"
        result.length shouldBe length
    }
    
    "padAround should return original string when length is less than string length" {
        // Arrange
        val input = "testing"
        val length = 3
        
        // Act
        val result = input.padAround(length)
        
        // Assert
        result shouldBe "testing"
        result.length shouldBe 7
    }
    
    "padAround should handle empty string" {
        // Arrange
        val input = ""
        val length = 6
        
        // Act
        val result = input.padAround(length)
        
        // Assert
        result shouldBe "      "
        result.length shouldBe length
    }
    
    // fillAround tests
    "fillAround should add specified number of characters on both sides" {
        // Arrange
        val input = "test"
        val length = 3
        
        // Act
        val result = input.fillAround(length)
        
        // Assert
        result shouldBe "   test   "
        result.length shouldBe input.length + (length * 2)
    }
    
    "fillAround should use specified fill character" {
        // Arrange
        val input = "test"
        val length = 2
        val fillChar = '#'
        
        // Act
        val result = input.fillAround(length, fillChar)
        
        // Assert
        result shouldBe "##test##"
        result.length shouldBe input.length + (length * 2)
    }
    
    "fillAround should handle empty string" {
        // Arrange
        val input = ""
        val length = 4
        
        // Act
        val result = input.fillAround(length)
        
        // Assert
        result shouldBe "        "
        result.length shouldBe length * 2
    }
    
    "fillAround should handle zero length" {
        // Arrange
        val input = "test"
        val length = 0
        
        // Act
        val result = input.fillAround(length)
        
        // Assert
        result shouldBe "test"
        result.length shouldBe input.length
    }
    
    // splitLines tests
    "splitLines should split text by newlines" {
        // Arrange
        val input = "line1\nline2\nline3"
        
        // Act
        val result = input.splitLines()
        
        // Assert
        result shouldBe listOf("line1", "line2", "line3")
    }
    
    "splitLines should trim whitespace from lines" {
        // Arrange
        val input = "  line1  \n  line2  \n  line3  "
        
        // Act
        val result = input.splitLines()
        
        // Assert
        result shouldBe listOf("line1", "line2", "line3")
    }
    
    "splitLines should filter empty lines" {
        // Arrange
        val input = "line1\n\n\nline2\n\nline3"
        
        // Act
        val result = input.splitLines()
        
        // Assert
        result shouldBe listOf("line1", "line2", "line3")
    }
    
    "splitLines should handle empty string" {
        // Arrange
        val input = ""
        
        // Act
        val result = input.splitLines()
        
        // Assert
        result shouldBe emptyList()
    }
    
    "splitLines should handle string with only whitespace and newlines" {
        // Arrange
        val input = "  \n  \n  "
        
        // Act
        val result = input.splitLines()
        
        // Assert
        result shouldBe emptyList()
    }
})
