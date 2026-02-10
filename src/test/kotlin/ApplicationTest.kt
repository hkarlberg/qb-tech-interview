package se.orthogonal

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import se.orthogonal.benford.BenfordInput
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `root url should return 404`() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun `benford-analyse url should return 200`() = testApplication {
        application {
            module()
        }
        client.post("/benford/analyse", ) {
            contentType(Json)
            setBody("{\"text\":\"a:1\",\"sig\":0.2}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
