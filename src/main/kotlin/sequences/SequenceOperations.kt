package sequences

import Section
import atomicTest.eq
import runAll

class SequenceOperations {
    val list = (1..100_000).toList()
    val sequence = list.asSequence()

    private fun intermediateOperations() {
        //intermediate sequence operations return another sequence as a result
        //filter() and map() are intermediate operations, nothing happens until a result is asked for
        //the new sequence stores all info about postponed operations and will perform them only when needed
        val storedInfo = sequence.filter { it % 2 == 0 }.map { it * it }
        storedInfo::class.simpleName eq "TransformingSequence"
    }

    private fun terminalOperations() {
        //terminal sequence operations return a non-sequence as a result (such as any() or toList())
        //this is done by executing all stored computations
        val nonSequence = sequence.filter { it % 2 == 0 }.any { it == 2 }
        nonSequence::class.simpleName eq "Boolean"
    }

    init {
        runAll(
            "Sequence operations",
            Section("Intermediate operations", ::intermediateOperations),
            Section("Terminal operations", ::terminalOperations),
        )
    }
}