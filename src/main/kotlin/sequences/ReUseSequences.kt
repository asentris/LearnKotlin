package sequences

import atomicTest.eq

class ReUseSequences {
    //sequences can only be "consumed" ONE TIME
    fun reUseSequences() {
        val blank1 = mutableListOf<Int>()

        val untilNull = generateSequence(2) { it + 1 }

        val copy1 = untilNull.take(10).map { it }.filter { it % 2 == 0 }.drop(2)
        val copy2 = untilNull.take(10).map { it }.filter { it % 2 == 0 }.drop(2)
        copy1.toList() eq listOf(6, 8, 10)
        copy2.toList() eq listOf(6, 8, 10)

        //terminal operations can only be performed once per sequence
        val newList = copy1.toList()
        copy1.mapTo(blank1) { it }
        copy2.filterTo(blank1) { it == it }
        untilNull.first() eq 2
        untilNull.indexOf(10) eq 8
        untilNull.first()

        //any further attempt at using a terminal operation will result in a runtime error
    }
}