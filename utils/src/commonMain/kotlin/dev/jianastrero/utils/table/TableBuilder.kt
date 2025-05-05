package dev.jianastrero.utils.table

import dev.jianastrero.utils.ext.fillAround
import dev.jianastrero.utils.ext.padAround
import kotlin.math.max

/**
 * Data class representing a cell in a table.
 *
 * @property text The content of the cell
 * @property span The number of columns this cell spans (defaults to 1)
 */
data class Cell(
    val text: String,
    val span: Int = 1,
)

/**
 * Builder for creating header rows in a table.
 */
class HeaderBuilder internal constructor() {
    /**
     * The list of cells in this header row.
     */
    val cells = mutableListOf<Cell>()

    /**
     * Adds a cell to the header row.
     *
     * @param text The content of the cell
     * @param span The number of columns this cell spans (defaults to 1)
     */
    fun cell(text: String, span: Int = 1) {
        cells += Cell(text, span)
    }
}

/**
 * Builder for creating item rows in a table.
 */
class ItemBuilder internal constructor() {
    /**
     * The list of cells in this item row.
     */
    val cells = mutableListOf<Cell>()

    /**
     * Adds a cell to the item row.
     *
     * @param text The content of the cell
     * @param span The number of columns this cell spans (defaults to 1)
     */
    fun cell(text: String, span: Int = 1) {
        cells += Cell(text, span)
    }
}

/**
 * Sealed interface representing a row in a table.
 */
internal sealed interface Row {
    /**
     * The list of cells in this row.
     */
    val cells: List<Cell>

    /**
     * Represents a header row in a table.
     *
     * @property cells The list of cells in this header row
     */
    data class Header(override val cells: List<Cell>) : Row

    /**
     * Represents an item row in a table.
     *
     * @property cells The list of cells in this item row
     */
    data class Item(override val cells: List<Cell>) : Row
}

/**
 * Creates a formatted text table with the specified configuration.
 *
 * @param columnCount The number of columns in the table
 * @param tokens The set of characters to use for drawing the table (defaults to [TableTokens.Thin])
 * @param block The configuration block for building the table
 * @return A formatted string representation of the table
 * @throws Nothing This function does not throw any exceptions
 *
 * @see TableBuilder
 * @see TableTokens
 *
 * @example
 * ```
 * val tableString = table(2) {
 *     header {
 *         cell("Name")
 *         cell("Age")
 *     }
 *     item {
 *         cell("John")
 *         cell("25")
 *     }
 *     item {
 *         cell("Jane")
 *         cell("30")
 *     }
 * }
 * ```
 */
fun table(
    columnCount: Int,
    tokens: TableTokens = TableTokens.Thin,
    block: TableBuilder.() -> Unit
): String {
    val builder = TableBuilder(columnCount, tokens).apply(block)
    return builder.build()
}

/**
 * Builder class for creating formatted text tables.
 *
 * This class provides methods for adding header and item rows to a table,
 * and for building the final formatted table string.
 *
 * @property columnCount The number of columns in the table
 * @property tokens The set of characters to use for drawing the table
 */
class TableBuilder internal constructor(
    val columnCount: Int,
    val tokens: TableTokens
) {
    private val headers = mutableListOf<Row.Header>()
    private val items = mutableListOf<Row.Item>()

    /**
     * Builds the final formatted table string.
     *
     * @return A formatted string representation of the table
     */
    fun build(): String {
        val headerLengths = calculateMaxColumnLengths(headers)
        val itemLengths = calculateMaxColumnLengths(items)

        val innerBoxLength = headerLengths.zip(itemLengths).sumOf { (a, b) -> maxOf(a, b) } + columnCount - 1
        val columnLengths = calculateColumnLengths(headerLengths, itemLengths)

        val table = StringBuilder()

        // TOP
        table.append(tokens.topLeft)
        table.append(tokens.horizontal.repeat(innerBoxLength))
        table.append(tokens.topRight)

        // HEADERS
        headers.forEach { header ->
            table.appendLine()
            table.append(tokens.vertical)
            val header = header.cells.mapIndexed { index, cell ->
                val length = Array(cell.span) { columnLengths[index + it] }.sum() + cell.span - 1
                cell.text.padAround(length)
            }.joinToString(tokens.vertical)
            table.append(header)
            table.append(tokens.vertical)
        }

        // SEPARATOR
        table.appendLine()
        table.append(tokens.vertical)
        table.append(tokens.horizontal.repeat(innerBoxLength))
        table.append(tokens.vertical)

        // PROPERTIES
        items.forEach { item ->
            table.appendLine()
            table.append(tokens.vertical)
            val item = item.cells.mapIndexed { index, cell ->
                val length = Array(cell.span) { columnLengths[index + it] }.sum() + cell.span - 1
                cell.text.padEnd(length)
            }.joinToString(tokens.vertical)
            table.append(item)
            table.append(tokens.vertical)
        }

        // BOTTOM
        table.appendLine()
        table.append(tokens.bottomLeft)
        table.append(tokens.horizontal.repeat(innerBoxLength))
        table.append(tokens.bottomRight)

        return table.toString()
    }

    private fun calculateMaxColumnLengths(
        list: List<Row>,
    ): List<Int> {
        val rowLengths = mutableListOf<List<Int>>()

        for (row in list) {
            val newRow = mutableListOf<Int>()
            for (cell in row.cells) {
                if (cell.span == 1) {
                    newRow += cell.text.length
                } else {
                    val cellLength = cell.text.length + cell.span - 1
                    val cellSpan = cell.span
                    val cellLengthPerSpan = cellLength / cellSpan
                    val cellRemainder = cellLength % cellSpan

                    for (i in 0 until cellSpan) {
                        newRow += if (i == cellSpan - 1) {
                            cellLengthPerSpan + cellRemainder
                        } else {
                            cellLengthPerSpan
                        }
                    }
                }
            }

            rowLengths += newRow
        }

        val maxRowLengths = mutableListOf<Int>()

        for (i in 0 until columnCount) {
            val maxLength = rowLengths.maxOfOrNull { it.getOrNull(i) ?: 0 } ?: 0
            maxRowLengths += maxLength
        }

        return maxRowLengths
    }

    private fun calculateColumnLengths(
        listA: List<Int>,
        listB: List<Int>,
    ): List<Int> {
        val maxLength = maxOf(listA.size, listB.size)
        val result = mutableListOf<Int>()

        for (i in 0 until maxLength) {
            val lengthA = listA.getOrNull(i) ?: 0
            val lengthB = listB.getOrNull(i) ?: 0
            result += maxOf(lengthA, lengthB)
        }

        return result
    }

    /**
     * Adds a header row to the table.
     *
     * @param block The configuration block for building the header row
     * @throws IllegalArgumentException if the sum of cell spans doesn't match the column count
     */
    fun header(block: HeaderBuilder.() -> Unit) {
        val headerBuilder = HeaderBuilder().apply(block)

        require(headerBuilder.cells.isEmpty() || headerBuilder.cells.sumOf { it.span } == columnCount) {
            "Header cells do not match column count"
        }

        val newCells = headerBuilder.cells.map { it.copy(it.text.fillAround(1)) }
        headers += Row.Header(newCells)
    }

    /**
     * Adds an item row to the table.
     *
     * @param block The configuration block for building the item row
     * @throws IllegalArgumentException if the sum of cell spans doesn't match the column count
     */
    fun item(block: ItemBuilder.() -> Unit) {
        val itemBuilder = ItemBuilder().apply(block)

        require(itemBuilder.cells.isEmpty() || itemBuilder.cells.sumOf { it.span } == columnCount) {
            "Item cells do not match column count"
        }

        val newCells = itemBuilder.cells.map { it.copy(it.text.fillAround(1)) }
        items += Row.Item(newCells)
    }
}
