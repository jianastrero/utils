package dev.jianastrero.utils.table

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TableTokensTest : StringSpec({
    "Thin tokens should have correct characters" {
        // Arrange & Act
        val tokens = TableTokens.Thin
        
        // Assert
        tokens.topLeft shouldBe "┌"
        tokens.topRight shouldBe "┐"
        tokens.bottomLeft shouldBe "└"
        tokens.bottomRight shouldBe "┘"
        tokens.vertical shouldBe "│"
        tokens.verticalLeft shouldBe "┤"
        tokens.verticalRight shouldBe "├"
        tokens.horizontal shouldBe "─"
        tokens.horizontalDown shouldBe "┬"
        tokens.horizontalUp shouldBe "┴"
        tokens.cross shouldBe "┼"
    }
    
    "Thick tokens should have correct characters" {
        // Arrange & Act
        val tokens = TableTokens.Thick
        
        // Assert
        tokens.topLeft shouldBe "┏"
        tokens.topRight shouldBe "┓"
        tokens.bottomLeft shouldBe "┗"
        tokens.bottomRight shouldBe "┛"
        tokens.vertical shouldBe "┃"
        tokens.verticalLeft shouldBe "┫"
        tokens.verticalRight shouldBe "┣"
        tokens.horizontal shouldBe "━"
        tokens.horizontalDown shouldBe "┳"
        tokens.horizontalUp shouldBe "┻"
        tokens.cross shouldBe "╋"
    }
})
