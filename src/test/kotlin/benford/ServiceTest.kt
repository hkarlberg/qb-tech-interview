package se.orthogonal.benford

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ServiceTest {

    val benfordScaled = benfordDistribution
        .mapValues { it.value*10e3 }
        .mapValues { it.value.toInt() }
        .mapValues { (1..it.value).map { _ -> it.key } }
        .flatMap { it.value }

    val benfordScaledInput = benfordScaled.joinToString(",") { "text:$it" }

    @Test
    fun `analyse should return false given zero distribution`() {
        val output = analyse(BenfordInput("", 0.0))

        assertFalse(output.pass)
    }

    @Test
    fun `analyse should return true when significance value is one, given any non-zero distribution`() {
        val output = analyse(BenfordInput("a:9", 1.0))

        assertTrue(output.pass)
    }

    @Test
    fun `analyse should return false when significance value is zero, given benford distribution`() {
        val output = analyse(BenfordInput(benfordScaledInput, 0.0))

        assertFalse(output.pass)
    }

    @Test
    fun `analyse should return true when significance value is low, given benford distribution`() {
        val output = analyse(BenfordInput(benfordScaledInput, 0.00001))

        assertTrue(output.pass)
    }

}