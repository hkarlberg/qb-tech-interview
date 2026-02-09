package se.orthogonal

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.server.testing.*
import org.apache.commons.math3.stat.inference.ChiSquareTest
import se.orthogonal.benford.BenfordInput
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
        BenfordInput("a:1,b:1,c:2,d:4", 0.2)
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
