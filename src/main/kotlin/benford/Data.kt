package se.orthogonal.benford

data class BedfordInput (
    val text: String,
    val sig: Double
)

data class BedfordOutput (
    val pass: Boolean,
    val expectedDistribution: Collection<Double>,
    val distribution: Collection<Double>
)
