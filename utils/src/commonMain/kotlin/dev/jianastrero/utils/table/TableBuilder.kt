package dev.jianastrero.utils.table

import dev.jianastrero.utils.ext.fillAround
import dev.jianastrero.utils.ext.padAround

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
        cells.add(Cell(text, span))
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
        cells.add(Cell(text, span))
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
        val innerBoxLength = calculateInnerBoxLength()
        val columnLengths = Array(columnCount) {
            calculateColumnLength(columnIndex = it, innerBoxLength = innerBoxLength)
        }

        val table = StringBuilder()

        // TOP
        table.append(tokens.topLeft)
        table.append(tokens.horizontal.repeat(innerBoxLength))
        table.append(tokens.topRight)

        // HEADERS
        headers.forEach { header ->
            table.appendLine()
            table.append(tokens.vertical)
            val header = header.cells.joinToString(tokens.vertical) { cell ->
                val cellLength = Array(cell.span) { columnLengths[it] }.sumOf { it } + cell.span - 1
                cell.text.padAround(cellLength)
            }
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
                val cellLength = columnLengths.slice(index until index + cell.span).sum() + cell.span - 1
                cell.text.padEnd(cellLength)
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

    /**
     * Calculates the inner box length of the table.
     *
     * @return The total length of the inner content area of the table
     */
    private fun calculateInnerBoxLength(): Int {
        val headerMaxLength = Array(columnCount) {
            calculateColumnLength(headers, it, excludeMultiColumnCells = false)
        }.sum()
        val headerAdditionalLength = columnCount - (headers.flatMap { it.cells.map { it.span } }.minOrNull() ?: 0)
        val itemMaxLength = Array(columnCount) {
            calculateColumnLength(items, it, excludeMultiColumnCells = false)
        }.sum()
        val itemAdditionalLength = columnCount - (items.flatMap { it.cells.map { it.span } }.minOrNull() ?: 0)

        return maxOf(headerMaxLength + headerAdditionalLength, itemMaxLength + itemAdditionalLength)
    }

    /**
     * Calculates the length of a specific column.
     *
     * @param columnIndex The index of the column
     * @param innerBoxLength The total inner box length (optional)
     * @param excludeMultiColumnCells Whether to exclude cells that span multiple columns (defaults to true)
     * @return The calculated length for the column
     */
    private fun calculateColumnLength(
        columnIndex: Int,
        innerBoxLength: Int? = null,
        excludeMultiColumnCells: Boolean = true
    ): Int {
        val maxHeaderLength = calculateColumnLength(
            list = headers,
            columnIndex = columnIndex,
            innerBoxLength = innerBoxLength,
            excludeMultiColumnCells = excludeMultiColumnCells,
        )
        val maxItemLength = calculateColumnLength(
            list = items,
            columnIndex = columnIndex,
            innerBoxLength = innerBoxLength,
            excludeMultiColumnCells = excludeMultiColumnCells,
        )

        return maxOf(maxHeaderLength, maxItemLength)
    }

    /**
     * Calculates the length of a specific column in a list of rows.
     *
     * @param list The list of rows to calculate the column length for
     * @param columnIndex The index of the column
     * @param innerBoxLength The total inner box length (optional)
     * @param excludeMultiColumnCells Whether to exclude cells that span multiple columns (defaults to true)
     * @return The calculated length for the column
     */
    private fun calculateColumnLength(
        list: List<Row>,
        columnIndex: Int,
        innerBoxLength: Int? = null,
        excludeMultiColumnCells: Boolean = true,
    ): Int {
        if (innerBoxLength != null && columnIndex == columnCount - 1 && columnCount > 1) {
            var otherLength = 0
            for (i in 0 until columnCount - 1) {
                otherLength += calculateColumnLength(
                    list = list,
                    columnIndex = i,
                    innerBoxLength = null,
                    excludeMultiColumnCells = excludeMultiColumnCells
                )
            }
            if (otherLength == 0) return 0
            otherLength += columnCount - 1
            return innerBoxLength - otherLength
        }
        return list.mapNotNull { it.cells.getOrNull(columnIndex) }
            .run { if (excludeMultiColumnCells) filter { it.span == 1 } else this }
            .maxOfOrNull { it.text.length } ?: 0
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
        headers.add(Row.Header(newCells))
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
        items.add(Row.Item(newCells))
    }
}
