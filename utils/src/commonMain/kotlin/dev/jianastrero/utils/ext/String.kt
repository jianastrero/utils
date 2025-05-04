package dev.jianastrero.utils.ext

fun String.padAround(
    length: Int,
    padChar: Char = ' ',
): String {
    val padString = padChar.toString()
    val remainingLength = length - this.length
    val padLeft = (remainingLength / 2).coerceIn(0, length)
    val padRight = (remainingLength - padLeft).coerceIn(0, length)

    return "${padString.repeat(padLeft)}$this${padString.repeat(padRight)}"
}

fun String.fillAround(
    length: Int,
    fillChar: Char = ' ',
): String {
    val fillString = fillChar.toString()
    return "${fillString.repeat(length)}$this${fillString.repeat(length)}"
}

fun String.splitLines(): List<String> {
    return this.split("\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
}
