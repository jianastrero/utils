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
}
