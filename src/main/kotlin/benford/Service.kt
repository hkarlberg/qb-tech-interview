package se.orthogonal.benford

import org.apache.commons.math3.stat.inference.ChiSquareTest
import java.util.SortedMap
import kotlin.collections.filter
import kotlin.collections.mapValues

fun analyse(input: BenfordInput): BenfordOutput {
    val counts = input.extractNumbers()
                      .extractLeadingDigit()
                      .countDigits()

    val pValue = ChiSquareTest().chiSquareTest(
        benfordDistribution.values.toDoubleArray(),
        counts.map { it.toLong() }.toLongArray()
    )

    val sum = counts.sum()

    return BenfordOutput(
        !pValue.isNaN() && pValue > 1 - input.sig,
        benfordDistribution.values,
        counts.map { it.toDouble() / sum }
    )
}

private fun BenfordInput.extractNumbers() =
    this.text
        .split("[^a-zA-Z\\d]".toRegex())
        .filter { it.isNotBlank() }
        .chunked(2)
        .map { (_, value) -> value }

private fun Collection<String>.extractLeadingDigit() =
    this.map { findFirstNonZero(it) }
        .filter { it.isDigit() }
        .map { it.digitToInt() }
        .filter { it > 0 }

private tailrec fun findFirstNonZero(entry: String): Char =
    if (entry.first().equals('0', true)) {
        findFirstNonZero(entry.drop(1))
    } else {
        entry.first()
    }

private fun Collection<Int>.countDigits() =
    this.groupByTo(generateBins()) { it }
        .mapValues { it.value.size }
        .values

private fun generateBins(): SortedMap<Int, MutableList<Int>> = sortedMapOf(
    1 to mutableListOf(),
    2 to mutableListOf(),
    3 to mutableListOf(),
    4 to mutableListOf(),
    5 to mutableListOf(),
    6 to mutableListOf(),
    7 to mutableListOf(),
    8 to mutableListOf(),
    9 to mutableListOf()
)
