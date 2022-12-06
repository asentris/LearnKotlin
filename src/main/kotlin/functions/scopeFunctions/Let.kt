package functions.scopeFunctions

import Section
import atomicTest.eq
import runAll

//executes all functions within its scope and returns the final return value
//same as "run", but used when not wanting to shadow "this"
//additionally can be used with null safe access operator
class Let {

    private fun useLet() {
        MyScopeClass().let {
            it.text = "Bye"
            it.number = 99

            it.text eq "Bye"
            it.number eq 99
            it.addition(1, 2) eq 3
            it.subtraction(1, 2) eq -1
            it.multiplication(1, 2) eq 2
            it.division(1, 2) eq 0

            it::class eq MyScopeClass()::class
            this::class eq Let()::class

            it.addition(1, 2)
        } eq 3

        MyScopeClass().nullable?.let { }

        let {
            MyScopeClass().addition(2, 3)
            MyScopeClass().subtraction(6, 2)
        } eq 4
    }

    private fun dontUseLet() {
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
            "Let examples",
            Section("Using let", ::useLet),
            Section("Not using let", ::dontUseLet),
        )
    }
}