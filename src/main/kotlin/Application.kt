package se.orthogonal

import io.ktor.server.application.*
import se.orthogonal.openapi.configureHTTP
import se.orthogonal.benford.configureRouting
import se.orthogonal.security.configureSecurity

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureRouting()
}
