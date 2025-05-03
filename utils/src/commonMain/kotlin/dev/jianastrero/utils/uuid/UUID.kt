package dev.jianastrero.utils.uuid

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun generateUUID(dashed: Boolean = true): String {
    val uuid = Uuid.random()
    return if (dashed) uuid.toHexDashString() else uuid.toHexString()
}
