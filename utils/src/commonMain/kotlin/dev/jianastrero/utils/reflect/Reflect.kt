package dev.jianastrero.utils.reflect

@Throws(IllegalStateException::class)
internal expect fun <T : Any> T.getProperties(): Map<String, Pair<String, Any?>>
