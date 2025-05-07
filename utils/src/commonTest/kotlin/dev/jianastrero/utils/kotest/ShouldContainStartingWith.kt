package dev.jianastrero.utils.kotest

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should

private fun containsStartingWith(expected: String): Matcher<Iterable<String>> = Matcher { actual ->
    MatcherResult(
        actual.any { it.startsWith(expected) },
        {
            val actualString = actual.joinToString(", ")
            "List [$actualString] should contain a string starting with $expected"
        },
        {
            val actualString = actual.joinToString(", ")
            "List [$actualString] should not contain a string starting with $expected"
        }
    )
}

infix fun Iterable<String>.shouldContainStartingWith(expected: String): Iterable<String> {
    this should containsStartingWith(expected)
    return this
}
