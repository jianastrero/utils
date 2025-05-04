package dev.jianastrero.utils.table

import dev.jianastrero.utils.ext.fillAround
import dev.jianastrero.utils.ext.padAround

class Table private constructor() {

    data class Cell(val text: String)

    sealed interface Row {
        data class Header(override val cells: List<Cell>) : Row
        data class Item(override val cells: List<Cell>) : Row

        val cells: List<Cell>
    }

    class Builder(
        tokens: TableTokens
    ) {
        private val table = Table().apply {
            this.tokens = tokens
        }

        fun build(): Table = table

        fun header(vararg columns: String) {
            val newColumns = columns.map { it.trim() }.filter { it.isNotBlank() }
            require(newColumns.isNotEmpty()) { "Header columns cannot be empty" }
            require(newColumns.none { it.contains("\n") }) { "Header columns cannot contain new line" }
            table.rows += Row.Header(columns.map { Cell(it.fillAround(1)) })
        }

        fun item(vararg cells: String) {
            val newCells = cells.map { it.trim() }.filter { it.isNotBlank() }
            require(newCells.isNotEmpty()) { "Item cells cannot be empty" }
            require(newCells.none { it.contains("\n") }) { "Item cells cannot contain new lines" }
            table.rows += Row.Item(newCells.map { Cell(it.fillAround(1)) })
        }
    }

    private var tokens: TableTokens = TableTokens.Thin
    private var rows: List<Row> = emptyList()

    private fun List<Row>.asStrings(): List<List<String>> = map { it.cells.map { it.text } }

    private val headerInstances: List<List<String>>
        get() = rows.filterIsInstance<Row.Header>().asStrings()

    private val itemInstances: List<List<String>>
        get() = rows.filterIsInstance<Row.Item>().asStrings()

    private val List<List<String>>.maxLength: Int
        get()  = maxOfOrNull { it.sumOf { it.length } + it.size - 1 } ?: 0

    private val innerLength: Int
        get() = maxOf(headerInstances.maxLength, itemInstances.maxLength)

    private fun List<List<String>>.rows(centered: Boolean): List<Pair<String, List<Int>>> {
        val rows = mutableListOf<Pair<String, List<Int>>>()

        val maxLength = this.maxLength

        for (row in this) {
            val rowLength = row.sumOf { it.length } + row.size - 1
            val weights = row.map { it.length / rowLength.toFloat() }

            val rowText = row.zip(weights).joinToString(tokens.vertical) { (cell, weight) ->
                val length = (weight * maxLength).toInt()

                cell.padAround(length)
            }
            val stops = rowText.mapIndexedNotNull { index, c ->
                if (c == tokens.vertical[0]) index else null
            }
            rows += rowText to stops
        }

        return rows
    }

    fun rowItems(): List<String> {
        val headerRows = headerInstances.rows(true)
        val headerTopStops = headerRows.firstOrNull()?.second ?: emptyList()
        val headerBottomStops = headerRows.lastOrNull()?.second ?: emptyList()

        val headers = headerRows.map { (text, _) ->
            tokens.middle(text)
        }

        val items = mutableListOf<String>()
        items += tokens.top(innerLength, facingBottom = headerTopStops)
        items += headers
        items += tokens.separator(innerLength, facingTop = headerBottomStops)
        items += tokens.bottom(innerLength)

        return items
    }

}

fun table(
    tokens: TableTokens,
    block: Table.Builder.() -> Unit
): List<String> =
    Table.Builder(tokens = tokens)
        .apply(block)
        .build()
        .rowItems()
