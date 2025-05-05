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

    "table with operator overloads in header should be formatted correctly" {
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
                +"Name"
                +"Age"
            }
            item {
                cell("John")
                cell("25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with operator overloads in item should be formatted correctly" {
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
                +"John"
                +"25"
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with times operator in header should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌────────────────┐
            │ User │ Details │
            │────────────────│
            │ John │ 25      │
            └────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(2) {
            header {
                +"User"
                "Details" * 1
            }
            item {
                cell("John")
                cell("25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with times operator in item should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌────────────────┐
            │ User │ Details │
            │────────────────│
            │ John │ 25      │
            └────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(2) {
            header {
                cell("User")
                cell("Details")
            }
            item {
                +"John"
                "25" * 1
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with not operator for header should be formatted correctly" {
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
            !"User Info"
            item {
                cell("John, 25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with unaryPlus operator for item should be formatted correctly" {
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
            +"John, 25"
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with not operator for array of strings should be formatted correctly" {
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
            !arrayOf("Name", "Age")
            item {
                cell("John")
                cell("25")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with unaryPlus operator for array of strings should be formatted correctly" {
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
            +arrayOf("John", "25")
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with not operator for pair should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌──────────────────────┐
            │   User Information   │
            │──────────────────────│
            │ John │ 25   │ Admin  │
            └──────────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(3) {
            !("User Information" * 3)
            item {
                cell("John")
                cell("25")
                cell("Admin")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with unaryPlus operator for pair should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌──────────────────────┐
            │   User Information   │
            │──────────────────────│
            │ Contact Details      │
            └──────────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(3) {
            header {
                cell("User Information", 3)
            }
            +("Contact Details" * 3)
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with not operator for array of pairs should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌───────────────────┐
            │ Name │ Age │ Role │
            │───────────────────│
            │ John │ 25  │ Dev  │
            └───────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(3) {
            !arrayOf("Name" * 1, "Age" * 1, "Role" * 1)
            item {
                cell("John")
                cell("25")
                cell("Dev")
            }
        }

        // Assert
        actualTable shouldBe expectedTable
    }

    "table with unaryPlus operator for array of pairs should be formatted correctly" {
        // Arrange
        val expectedTable = """
            ┌───────────────────┐
            │ Name │ Age │ Role │
            │───────────────────│
            │ John │ 25  │ Dev  │
            └───────────────────┘
        """.trimIndent()

        // Act
        val actualTable = table(3) {
            header {
                cell("Name")
                cell("Age")
                cell("Role")
            }
            +arrayOf("John" * 1, "25" * 1, "Dev" * 1)
        }

        // Assert
        actualTable shouldBe expectedTable
    }
})
