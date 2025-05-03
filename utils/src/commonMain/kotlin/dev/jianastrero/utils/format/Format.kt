package dev.jianastrero.utils.format

private const val STRING_FORMAT = "%s"

fun String.format(
    vararg args: Any?,
): String {
    var string = this

    for (arg in args) {
        val index = string.indexOf(STRING_FORMAT)
        if (index == -1) break
        string = string.replaceRange(index, index + STRING_FORMAT.length, arg.toString())
    }

    return string
}

fun String.formatEmpty(): String {
    return replace(STRING_FORMAT, "")
}
