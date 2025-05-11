package dev.jianastrero.utils.reflect

actual fun <T : Any> T.getProperties(): Map<String, Pair<String, Any?>> {
    error("getProperties is not supported on Native platform")
}
