package dev.jianastrero.utils.reflect

@Throws(IllegalStateException::class)
actual fun <T : Any> T.getProperties(): Map<String, Pair<String, Any?>> {
    error("getProperties is not supported on Native platform")
}
