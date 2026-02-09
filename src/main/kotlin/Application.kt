package se.orthogonal

import io.ktor.server.application.*
import se.orthogonal.benford.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
