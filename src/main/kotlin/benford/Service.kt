package se.orthogonal.benford

import org.apache.commons.math3.stat.inference.ChiSquareTest
import java.util.SortedMap
import kotlin.collections.filter
import kotlin.collections.mapValues

val benfordDist: Map<Int, Double> = mapOf(
    1 to 0.3010299956639812,
    2 to 0.17609125905568124,
    3 to 0.12493873660829993,
    4 to 0.09691001300805642,
    5 to 0.07918124604762482,
    6 to 0.06694678963061322,
    7 to 0.05799194697768673,
    8 to 0.05115252244738129,
    9 to 0.04575749056067514
)

fun analyse(input: BenfordInput): BenfordOutput {
    print("input:$input")
    val counts = input.extractNumbers()
                      .extractLeadingDigit()
                      .countDigits()

    val pValue = ChiSquareTest().chiSquareTest(
        benfordDist.values.toDoubleArray(),
        counts.map { it.toLong() }.toLongArray()
    )

    val sum = counts.sum()

    return BenfordOutput(
        pValue < input.sig,
        benfordDist.values,
        counts.map { it.toDouble() / sum }
    )
}

private fun BenfordInput.extractNumbers() =
    this.text
        .split("[^a-zA-Z\\d]".toRegex())
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
    this.groupByTo(getBins()) { it }
        .mapValues { it.value.size }
        .values

private fun getBins(): SortedMap<Int, MutableList<Int>> = sortedMapOf(
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
