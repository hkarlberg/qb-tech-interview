package se.orthogonal.benford

import kotlinx.serialization.Serializable

@Serializable
data class BenfordInput (
    val text: String,
    val sig: Double
)

@Serializable
data class BenfordOutput (
    val pass: Boolean,
    val expectedDistribution: Collection<Double>,
    val distribution: Collection<Double>
)
