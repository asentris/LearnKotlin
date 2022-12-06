package containers

import atomicTest.eq

//Sets only allow one element of each value, so there can't be two Integers with a value of 1 for example.
class Sets {
    var readOnlySet = setOf(1, 2, 3, 3) //elements canNOT be added or removed
    val mutableSet = mutableSetOf<Int>() //elements can be modified

    fun checkMembership() {
        readOnlySet eq "[1, 2, 3]"
        (2 in readOnlySet) eq true
        readOnlySet.contains(2) eq true
        readOnlySet.containsAll(setOf(1, 2)) eq true
    }

    fun readOnlySetMustReassign() {
        readOnlySet = readOnlySet.union(setOf(2, 3, 4, 5)) //setOf(1, 2, 3, 4, 5)
        readOnlySet = readOnlySet intersect setOf(3, 4) //[3, 4]
        readOnlySet = readOnlySet subtract setOf(0, 1, 3) //setOf(4)
        readOnlySet = readOnlySet - setOf(4, 5, 6) //[]
        readOnlySet = readOnlySet + setOf(1, 2, 3)
        readOnlySet.map { "[$it]" } eq listOf("[1]", "[2]", "[3]") //returns list, not set
        readOnlySet.filter { it > 2 } eq listOf(3) //returns list, not set
    }

    fun mutableSetFunctions() {
        mutableSet += 42 //[42]
        mutableSet -= 42 //setOf<Int>
    }

    fun convertToSet() {
        val myList = listOf(1, 1, 2, 2, 3, 3) //[1, 1, 2, 2, 3, 3]
        val convertedList = myList.toSet() //[1, 2, 3]
        val backToList = convertedList.distinct() //listOf(1, 2, 3)
        val convertedString = "ABCBA".toSet() //setOf('A', 'B', 'C')
    }
}