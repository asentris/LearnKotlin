package functions.scopeFunctions

import Section
import atomicTest.eq
import runAll

//executes all functions within its scope and returns the final return value
//same as "with", but in extension function format
//additionally can be used with null safe access operator
class Run {

    private fun useRun() {
        MyScopeClass().run {
            text = "Bye"
            number = 99

            text eq "Bye"
            number eq 99
            addition(1, 2) eq 3
            subtraction(1, 2) eq -1
            multiplication(1, 2) eq 2
            division(1, 2) eq 0

            this::class eq MyScopeClass()::class
            this@Run::class eq Run()::class

            addition(1, 2)
        } eq 3

        MyScopeClass().nullable?.run { }

        run {
            MyScopeClass().addition(2, 3)
            MyScopeClass().subtraction(6, 2)
        } eq 4
    }

    private fun dontUseRun() {
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
            "Run examples",
            Section("Using run", ::useRun),
            Section("Not using run", ::dontUseRun),
        )
    }
}