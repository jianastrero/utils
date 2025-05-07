package dev.jianastrero.utils.format

private data object FormatTokens {
    const val STRING = "%s"
    const val DECIMAL = "%d"
    const val FLOAT = "%f"
    const val CHAR = "%c"
    const val BOOLEAN = "%b"
    const val HEX = "%x"
    const val OCTAL = "%o"
    const val SCIENTIFIC = "%e"
    const val GENERAL = "%g"
    const val HEX_FLOAT = "%a"
    const val HASH_CODE = "%h"
    const val NEW_LINE = "%n"
    const val DATE_TIME = "%t"
    const val PERCENT = "%%"
}

fun String.format(
    vararg args: Any?,
): String {
    var string = this
    val tokens = listOf(
        FormatTokens.STRING,
        FormatTokens.DECIMAL,
        FormatTokens.FLOAT,
        FormatTokens.CHAR,
        FormatTokens.BOOLEAN,
        FormatTokens.HEX,
        FormatTokens.OCTAL,
        FormatTokens.SCIENTIFIC,
        FormatTokens.GENERAL,
        FormatTokens.HEX_FLOAT,
        FormatTokens.HASH_CODE,
        FormatTokens.DATE_TIME
    )

    for (arg in args) {
        var replaced = false
        for (token in tokens) {
            val index = string.indexOf(token)
            if (index != -1) {
                string = string.replaceRange(index, index + token.length, arg.toString())
                replaced = true
                break
            }
        }
        if (!replaced) break
    }

    return string.formatEmpty()
}

fun String.formatEmpty(): String {
    var result = this
    val tokens = listOf(
        FormatTokens.STRING,
        FormatTokens.DECIMAL,
        FormatTokens.FLOAT,
        FormatTokens.CHAR,
        FormatTokens.BOOLEAN,
        FormatTokens.HEX,
        FormatTokens.OCTAL,
        FormatTokens.SCIENTIFIC,
        FormatTokens.GENERAL,
        FormatTokens.HEX_FLOAT,
        FormatTokens.HASH_CODE,
        FormatTokens.DATE_TIME
    )

    for (token in tokens) {
        result = result.replace(token, "")
    }

    // Handle special tokens
    result = result.replace(FormatTokens.NEW_LINE, "\n")
    result = result.replace(FormatTokens.PERCENT, "%")

    return result
}
