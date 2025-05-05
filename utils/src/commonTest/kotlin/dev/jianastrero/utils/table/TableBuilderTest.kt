package dev.jianastrero.utils.table

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TableBuilderTest : StringSpec({
    "table with single header and item should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌────────────┐
            │ Name │ Age │
            │────────────│
            │ John │ 25  │
            └────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(2) {
            header {
                cell("Name")
                cell("Age")
            }
            item {
                cell("John")
                cell("25")
            }
        }
        println(expectedTable)
        println(actualTable)

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with multiple items should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌────────────┐
            │ Name │ Age │
            │────────────│
            │ John │ 25  │
            │ Jane │ 30  │
            └────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(2) {
            header {
                cell("Name")
                cell("Age")
            }
            item {
                cell("John")
                cell("25")
            }
            item {
                cell("Jane")
                cell("30")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with cell spanning should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌───────────┐
            │ User Info │
            │───────────│
            │ John, 25  │
            └───────────┘
        """.trimIndent()

        // Act
        val actualTable = table(1) {
            header {
                cell("User Info")
            }
            item {
                cell("John, 25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with thick tokens should use thick borders" {
        // Arrange
        val expectedTable = """
            ┏━━━━━━━━━━━━┓
            ┃ Name ┃ Age ┃
            ┃━━━━━━━━━━━━┃
            ┃ John ┃ 25  ┃
            ┗━━━━━━━━━━━━┛
        """.trimIndent()

        // Act
        val actualTable = table(2, TableTokens.Thick) {
            header {
                cell("Name")
                cell("Age")
            }
            item {
                cell("John")
                cell("25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }
})
