package containers

import atomicTest.eq
import atomicTest.trace

//this is useful for sending information along with result

class DestructuringDeclarations {
    fun unpackCreatePair() {
        val newPair: Pair<Int, String> = Pair(10, "Ten")
        val newPairAlternate = Pair(10, "Ten")
        val newPairTertiary = 10 to "Ten"

        fun createPair(a: Int): Pair<Int, String> = Pair(a, " o'clock")
        val (one, two) = createPair(5)

        one eq 5
        two eq " o'clock"
    }

    fun unpackCreateTriple() {
        val newTriple = Triple(10, "Ten", 10.0)

        fun createTriple(a: Int, b: Double): Triple<Int, String, Double> =
            Triple(a, " and ", b)
        val (one, two, three) = createTriple(5, 5.0)

        one eq 5
        two eq " and" //?????? it doesn't register space for some reason ??????
        three eq 5.0
        "This${two}that" eq "This and that" //?????? this registers the space ??????
    }

    fun unpackThisDataClass() {
        data class sampleDataClass(
            val a: Int,
            val b: Int,
            val c: Int,
        )
        val thisDataClass = sampleDataClass(10, 20, 30)
        val (one, two, three) = thisDataClass

        one eq 10
        two eq 20
        three eq 30
        val (_, twenty) = thisDataClass //the first identifier is omitted with an underscore
        //any identifiers at the end are omitted by leaving them blank
        twenty eq 20
    }

    fun unpackMap() {
        var mapResult = ""
        val map = mapOf(
            1 to "one",
            2 to "two",
        )

        for ((key, value) in map) {
            mapResult += "$key = $value, "
        }
        mapResult eq "1 = one, 2 = two,"
    }

    fun unpackListOfPairs() {
        var pairListResult = ""
        val listOfPairs = listOf(
            Pair(1, "one"),
            Pair(2, "two"),
        )

        for ((a, b) in listOfPairs) {
            pairListResult += "($a, $b), "
        }
        pairListResult eq "(1, one), (2, two),"
    }

    fun unpackList() {
        val list = listOf("a", "b", "c")

        for ((index, value) in list.withIndex()) {
            trace("$index:$value")
        }
        trace eq "0:a 1:b 2:c"
    }
}