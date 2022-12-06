package classTypes.enums

import atomicTest.eq
import classTypes.enums.Enumerations.*

enum class Enumerations {
    One, Two, Three, Four
}

fun enumsFunctions() {
    One eq "One"
    One::class eq Enumerations::class
    Enumerations.values().toList() eq listOf(One, Two, Three, Four)
    One.ordinal eq 0
    Four.ordinal eq 3
}

//for example: whenEnumerations(Three)
fun whenEnumerations(enum: Enumerations): Int =
    when (enum) {
        One -> 1
        Two -> 2
        else -> 0
    }