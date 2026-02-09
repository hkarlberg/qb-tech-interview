package se.orthogonal.benford

import org.apache.commons.math3.stat.inference.ChiSquareTest
import kotlin.collections.filter
import kotlin.collections.mapValues

val bedfordDist: Map<Int, Double> = mapOf(
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

fun analyse(input: BedfordInput): BedfordOutput {
    print("input:$input")
    val counts = input.extractNumbers()
                      .extractLeadingDigit()
                      .countDigits()

    val pValue = ChiSquareTest().chiSquareTest(
        bedfordDist.values.toDoubleArray(),
        counts.map { it.toLong() }.toLongArray()
    )

    val sum = counts.sum()

    return BedfordOutput(
        pValue < input.sig,
        bedfordDist.values,
        counts.map { it.toDouble() / sum }
    )
}

private fun BedfordInput.extractNumbers() =
    this.text
        .split(',')
        .zipWithNext()
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
    this.groupByTo(sortedMapOf()){ it } //TODO: assert that it is sorted
        .mapValues { it.value.size }
        .values