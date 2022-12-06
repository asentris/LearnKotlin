package sequences

import atomicTest.eq
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class EvaluationTypes {
    val list = (1..100_000).toList()
    val sequence = list.asSequence()

    //also called horizontal evaluation:
    private fun eagerEvaluation(): Boolean {
        //each line is executed in full before moving to the next line
        return list.filter { it % 2 == 0 }  //filter() is executed first and sends listOf(2, 4) to map()
            .map { it * it }                //map() is executed seconds and sends listOf(4, 16) to any()
            .any { it > 10 }                //any() is executed last and finds the value 4 to be true
    }

    //also called vertical evaluation:
    private fun lazyEvaluation(): Boolean {
        //each element is executed through all lines before moving to the next element
        return sequence.filter { it % 2 == 0 } //filter() is executed on each element until true, then moves to map()
            .map { it * it }    //map() is executed on the single element passed to it, then moves to any()
            .any { it > 10 }    //if any() is true, the entire operation is complete
    }

    @ExperimentalTime
    fun measureTime() {
        val eagerTime = measureTimedValue { eagerEvaluation() }
        val lazyTime = measureTimedValue { lazyEvaluation() }
        println("Eager time: ${eagerTime.duration}")
        println("Lazy time: ${lazyTime.duration}")
    }
}