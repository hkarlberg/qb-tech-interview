package se.orthogonal.benford

import kotlinx.serialization.json.Json
import kotlin.math.log10
import kotlin.test.Test
import kotlin.test.assertEquals

class ConstantsTest {

    @Test
    fun `distribution should equal definition`() {
        val expected = (1..9).map { it.toDouble() }.map { log10(1 + 1 / it) }

        assertEquals(
            expected,
            benfordDistribution.values.toList()
        )
    }
}