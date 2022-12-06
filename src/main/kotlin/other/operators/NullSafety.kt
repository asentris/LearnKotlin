package other.operators

import Section
import atomicTest.capture
import atomicTest.eq
import runAll

class NullSafety {

    private fun nullable() { // ? after type indicates that it can be null
        val a: Int? = null
        val b: Int? = 1
        a eq null
        b eq 1
    }

    private fun nonNullAssertion() { // !! states "I guarantee that variable is not null"
        var a: String? = "abc"
        a!! eq "abc"    //will produce NullPointerException if it's null
        a!!.length eq 3 //this is two operators: the non-null assertion (!!) and the deference (.)

        a = null
        //since 'b' only accepts non null Strings & a!! is "guaranteed" non null, a NullPointerException is thrown
        capture { val b: String = a!! } eq "NullPointerException"
    }

    private fun String.safeCallFunExtension() = "***"
    private fun safeCall() { // ?. makes a function safe by executing only if the input is not null
        val a: String? = "a"
        val b: String? = null
        a?.safeCallFunExtension() eq "***"
        b?.safeCallFunExtension() eq null
    }

    private fun elvisOperator(a: Int?) { // ?: returns left side, unless it's null, then returns the right side
        (a ?: 2) eq a
        null ?: 2 eq 2
        null ?: null eq null
    }

    fun safeCallAndElvis(s: String?) { //execute only if it's not null, otherwise provide non-null default
        val lengthDirty = if (s != null) s.length else 0
        val lengthClean = s?.length ?: 0
        lengthDirty eq lengthClean
        s?.length eq null
    }

    init {
        runAll(
            "Null safety",
            Section("Type is nullable", ::nullable),
            Section("Non-null assertions", ::nonNullAssertion),
            Section("Safe calls", ::safeCall),
            Section("Elvis operator", { elvisOperator(1) }),
            Section("Combine safe call and elvis operator", { safeCallAndElvis("a") }),
        )
    }
}