package dev.jianastrero.utils.trace

import dev.jianastrero.utils.kotest.shouldContainStartingWith
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

class JvmStackTraceTest : StringSpec({
    "getStackTrace should be empty and head trace should be correct" {
        // Arrange
        val headTraceStart = "Thread.java → " +
                "java.lang.Thread → " +
                "getStackTrace → " +
                "line"
        val headTrace = "JvmStackTraceTest.kt → dev.jianastrero.utils.trace.JvmStackTraceTest"
        val stringScopeTrace = "StringSpecRootScope.kt → io.kotest.core.spec.style.scopes.StringSpecRootScope"

        // Act
        val result = getStackTrace()

        // Assert
        result shouldNotBe emptyList<String>()
        result shouldContainStartingWith headTrace
        result shouldContainStartingWith stringScopeTrace
        result[0] shouldStartWith headTraceStart
    }

    "getCaller should not be empty and head trace should be correct" {
        // Arrange
        val headTraceStart = "Thread.java → " +
                "java.lang.Thread → " +
                "getStackTrace → " +
                "line"

        // Act
        val result = getCaller()

        // Assert
        result shouldStartWith headTraceStart
    }
})
