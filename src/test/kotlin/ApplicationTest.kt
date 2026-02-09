package se.orthogonal

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.apache.commons.math3.stat.inference.ChiSquareTest
import kotlin.math.log10
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testDist() {
        print((1..9)
            .map { it.toDouble() }
            .map { log10(1 + 1/it) })
    }

    @Test
    fun testChiSquare() {

    }

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
