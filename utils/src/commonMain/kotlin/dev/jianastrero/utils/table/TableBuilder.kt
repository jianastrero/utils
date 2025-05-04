package dev.jianastrero.utils.table

import dev.jianastrero.utils.ext.fillAround
import dev.jianastrero.utils.ext.orZero
import dev.jianastrero.utils.ext.padAround

fun table(
    columnCount: Int,
    tokens: TableTokens = TableTokens.Thin,
    block: TableBuilder.() -> Unit
): String {
    val builder = TableBuilder(columnCount, tokens).apply(block)
    return builder.build()
}

class TableBuilder internal constructor(
    val columnCount: Int,
    val tokens: TableTokens
) {
    private val headers = mutableListOf<Row.Header>()
    private val items = mutableListOf<Row.Item>()

    fun header(block: HeaderBuilder.() -> Unit) {
        val headerBuilder = HeaderBuilder().apply(block)

        require(headerBuilder.cells.isEmpty() || headerBuilder.cells.sumOf { it.span } == columnCount) {
            "Header cells do not match column count"
        }

        val newCells = headerBuilder.cells.map { it.copy(it.text.fillAround(1)) }
        headers.add(Row.Header(newCells))
    }

    fun item(block: ItemBuilder.() -> Unit) {
        val itemBuilder = ItemBuilder().apply(block)

        require(itemBuilder.cells.isEmpty() || itemBuilder.cells.sumOf { it.span } == columnCount) {
            "Item cells do not match column count"
        }

        val newCells = itemBuilder.cells.map { it.copy(it.text.fillAround(1)) }
        items.add(Row.Item(newCells))
    }

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
}

class HeaderBuilder internal constructor() {
    val cells = mutableListOf<Cell>()

    fun cell(text: String, span: Int = 1) {
        cells.add(Cell(text, span))
    }
}

class ItemBuilder internal constructor() {
    val cells = mutableListOf<Cell>()

    fun cell(text: String, span: Int = 1) {
        cells.add(Cell(text, span))
    }
}

internal sealed interface Row {
    val cells: List<Cell>

    data class Header(override val cells: List<Cell>) : Row

    data class Item(override val cells: List<Cell>) : Row
}

data class Cell(
    val text: String,
    val span: Int = 1,
)
