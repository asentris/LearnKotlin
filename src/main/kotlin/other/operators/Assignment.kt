package other.operators

import Section
import atomicTest.eq
import runAll

class Assignment {

    private var a = 4 //default assignment operator

    private fun augmentedAssignmentOperators() {
        a += 4 //add, then assign
        a eq 8

        a -= 4 //subtract, then assign
        a eq 4

        a *= 2 //multiply, then assign
        a eq 8

        a /= 3 //divide, drop the remainder, then assign
        a eq 2

        a %= 3 //divide, keep only the remainder, then assign
        a eq 2
    }

    private fun incrementAndDecrementOperators() {
        var x = 4
        ++x eq 5 //prefix operator that first increments the value, then returns the result
        --x eq 4

        x++ eq 4 //postfix operator that first returns the initial value, then increments the variable
        x-- eq 5

        var y = 1
        y++ + ++y eq 4

        var z = 1
        z-- - --z eq 2
    }

    init {
        runAll(
            "Assignment operators",
            Section("Augmented assignment operators", ::augmentedAssignmentOperators),
            Section("Increment and decrement operators", ::incrementAndDecrementOperators),
        )
    }
}