package se.orthogonal.benford

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        post("/benford/analyse") {
            call.receive<BenfordInput>()
                .let { analyse(it) }
                .let { call.respond(it) }
        }
    }
}

