package dev.jianastrero.utils.table

/**
 * Defines character sets for drawing tables in text.
 *
 * This sealed class provides different styles of box-drawing characters
 * that can be used to create formatted tables in console output or text files.
 *
 * @property topLeft Character for the top-left corner of the table
 * @property topRight Character for the top-right corner of the table
 * @property bottomLeft Character for the bottom-left corner of the table
 * @property bottomRight Character for the bottom-right corner of the table
 * @property vertical Character for vertical lines
 * @property verticalLeft Character for T-junction with the vertical line on the left
 * @property verticalRight Character for T-junction with the vertical line on the right
 * @property horizontal Character for horizontal lines
 * @property horizontalDown Character for T-junction with the horizontal line on top
 * @property horizontalUp Character for T-junction with the horizontal line on bottom
 * @property cross Character for the intersection of horizontal and vertical lines
 *
 * @see TableBuilder
 */
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
    /**
     * A set of thin box-drawing characters for tables.
     *
     * Uses single-line box drawing characters for a lighter appearance.
     *
     * @example
     * ```
     * ┌───┬───┐
     * │ A │ B │
     * ├───┼───┤
     * │ 1 │ 2 │
     * └───┴───┘
     * ```
     */
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

    /**
     * A set of thick box-drawing characters for tables.
     *
     * Uses double-line box drawing characters for a heavier, more prominent appearance.
     *
     * @example
     * ```
     * ┏━━━┳━━━┓
     * ┃ A ┃ B ┃
     * ┣━━━╋━━━┫
     * ┃ 1 ┃ 2 ┃
     * ┗━━━┻━━━┛
     * ```
     */
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
