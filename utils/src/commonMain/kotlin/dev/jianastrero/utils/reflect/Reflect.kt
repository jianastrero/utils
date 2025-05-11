package dev.jianastrero.utils.reflect

internal expect fun <T : Any> T.getProperties(): Map<String, Pair<String, Any?>>
