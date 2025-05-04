package dev.jianastrero.utils.table

sealed class TableTokens(
    val topLeft: String,
    val topRight: String,
    val bottomLeft: String,
    val bottomRight: String,
    val vertical: String,
    val verticalLeft: String,
    val verticalRight: String,
    val horizontal: String,
    val horizontalDown: String,
    val horizontalUp: String,
    val cross: String,
) {
    data object Thin : TableTokens(
        topLeft = "┌",
        topRight = "┐",
        bottomLeft = "└",
        bottomRight = "┘",
        vertical = "│",
        verticalLeft = "┤",
        verticalRight = "├",
        horizontal = "─",
        horizontalDown = "┬",
        horizontalUp = "┴",
        cross = "┼",
    )

    data object Thick : TableTokens(
        topLeft = "┏",
        topRight = "┓",
        bottomLeft = "┗",
        bottomRight = "┛",
        vertical = "┃",
        verticalLeft = "┫",
        verticalRight = "┣",
        horizontal = "━",
        horizontalDown = "┳",
        horizontalUp = "┻",
        cross = "╋",
    )

    fun separator(
        innerLength: Int,
        facingBottom: List<Int> = emptyList(),
        facingTop: List<Int> = emptyList(),
    ): String {
        var string = verticalRight
        for (i in 0 until innerLength) {
            string += when {
                i in facingTop && i in facingBottom -> cross
                i in facingTop -> horizontalUp
                i in facingBottom -> horizontalDown
                else -> horizontal
            }
        }
        string += verticalLeft
        return string
    }

    fun top(
        innerLength: Int,
        facingBottom: List<Int> = emptyList()
    ): String {
        var string = topLeft
        for (i in 0 until innerLength) {
            string += when (i) {
                in facingBottom -> horizontalDown
                else -> horizontal
            }
        }
        string += topRight
        return string
    }

    fun bottom(
        innerLength: Int,
        facingTop: List<Int> = emptyList()
    ): String {
        var string = bottomLeft
        for (i in 0 until innerLength) {
            string += when (i) {
                in facingTop -> horizontalUp
                else -> horizontal
            }
        }
        string += bottomRight
        return string
    }

    fun middle(value: String): String {
        return "$vertical$value$vertical"
    }
}
