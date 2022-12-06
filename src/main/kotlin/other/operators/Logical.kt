package other.operators

import Section
import atomicTest.eq
import runAll

class Logical {

    private val a = String(charArrayOf('a', 'b', 'c'))
    private val b = a
    private val c = String(charArrayOf('a', 'b', 'c'))
    private val d = String(charArrayOf('a'))

    private fun structuralEquality() {
        (a == c) eq true    //equal to
        (a != d) eq true    //not equal to
    }

    private fun referentialEquality() {
        (a === b) eq true   //referenced "a" object equals "b"
        (a === c) eq false  //referenced "a" object equals "c" (false)
        (a !== c) eq true   //referenced "a" object does not equal "c"
    }

    private fun comparison() {
        !true eq false                      //! -> "not"
        (true && true) eq true              //&& -> "and", true only if expressions on both sides are true
        (false || true) eq true             //|| -> "or", true if either expression is true
        (false || true && true) eq true     //&& are evaluated before ||

        (1 < 2) eq true
        (2 > 1) eq true
        (1 <= 1) eq true
        (1 >= 1) eq true
    }

    init {
        runAll(
            "Logical operators",
            Section("Structural equality operators", ::structuralEquality),
            Section("Referential equality operators", ::referentialEquality),
            Section("Comparison operators", ::comparison),
        )
    }
}