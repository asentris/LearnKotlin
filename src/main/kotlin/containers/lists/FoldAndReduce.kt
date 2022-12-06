package containers.lists

import atomicTest.eq

class FoldAndReduce {
    fun test() {
        val list = listOf("B", "C", "D")

        list.fold("A") { acc, s -> "$acc$s" } eq "ABCD"
        list.foldRight("A") { s, acc -> "$acc$s" } eq "ADCB"

        //same as fold but initial value is first iteration in list
        list.reduce { acc, s -> "$acc$s" } eq "BCD"
        list.reduceRight { s, acc -> "$acc$s" } eq "DCB"

        //displays all successive operation results
        list.runningFold("A") { acc, s -> "$acc$s" } eq listOf("A", "AB", "ABC", "ABCD")
        list.runningReduce { acc, s -> "$acc$s" } eq listOf("B", "BC", "BCD")
    }
}