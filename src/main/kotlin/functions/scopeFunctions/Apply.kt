package functions.scopeFunctions

import Section
import atomicTest.eq
import runAll

//receiver & return object are both "this"
//this negates the need to write the object name repeatedly
class Apply {

    private fun useApply() {
        MyScopeClass().apply {
            text = "Bye"
            number = 99

            text eq "Bye"
            number eq 99
            addition(1, 2) eq 3
            subtraction(1, 2) eq -1
            multiplication(1, 2) eq 2
            division(1, 2) eq 0

            this::class eq MyScopeClass()::class
            this@Apply::class eq Apply()::class
        }
    }

    private fun dontUseApply() {
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
            "Apply examples",
            Section("Using apply", ::useApply),
            Section("Not using apply", ::dontUseApply),
        )
    }
}