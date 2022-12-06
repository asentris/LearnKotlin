package functions.scopeFunctions

import Section
import atomicTest.eq
import runAll

//executes all functions within its scope and returns the final return value
//same as "run", but in a regular function format
class With {

    private fun useWith() {
        with(MyScopeClass()) {
            text = "Bye"
            number = 99

            text eq "Bye"
            number eq 99
            addition(1, 2) eq 3
            subtraction(1, 2) eq -1
            multiplication(1, 2) eq 2
            division(1, 2) eq 0

            this::class eq MyScopeClass()::class
            this@With::class eq With()::class

            addition(1, 2)
        } eq 3
    }

    private fun dontUseWith() {
        val reWrite = MyScopeClass()
        reWrite.text = "Bye"
        reWrite.number = 99

        reWrite.text eq "Bye"
        reWrite.number eq 99
        reWrite.addition(1, 2) eq 3
        reWrite.subtraction(1, 2) eq -1
        reWrite.multiplication(1, 2) eq 2
        reWrite.division(1, 2) eq 0
    }

    init {
        runAll(
            "With examples",
            Section("Using with", ::useWith),
            Section("Not using with", ::dontUseWith),
        )
    }
}