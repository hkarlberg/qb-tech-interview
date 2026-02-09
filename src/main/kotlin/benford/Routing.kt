package se.orthogonal.benford

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlin.math.pow

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

fun Application.configureRouting() {
    routing {
        post("/benford/analyse") {
            val output: BedfordOutput = call.receive<BedfordInput>()
            call.respond(output)
        }
    }
}

fun analyse(input: BedfordInput): BedfordOutput {
    val count = input.text
        .split(',')
        .zipWithNext()
        .map { (_, value) -> value }
        .map { it.first() }
        .filter { it.isDigit() }
        .map { it.digitToInt() }
        .filter { it != 0 }
        .groupBy { it }
        .mapValues { it.value.size }
        .values

    val sum = count.sum()

    val chiSquare = count.zip(bedfordDist.values)
        .sumOf { (n, pBedford) -> (n - sum * pBedford).pow(2) / (sum * pBedford) }


}


data class BedfordInput (val text: String, val sig: Double)
data class BedfordOutput (val pass: Boolean, val chiSquare: Double, val pValue: Double)
