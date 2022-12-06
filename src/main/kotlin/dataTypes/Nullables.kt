package dataTypes

import atomicTest.eq
import atomicTest.trace

class Nullables {
    val nonNull1 = "abc"

    //val error1: String = null //compile-time error; must explicitly state it can be null
    val nullable1: String? = null
    val nullable2: String? = nonNull1

    //val error2: String = nullable2 //compile-time error; must explicitly state it can be null
    val nullable3 = nullable2
    fun testAssignments() {
        nonNull1 eq "abc"
        nullable1 eq null
        nullable2 eq "abc"
        nullable3 eq "abc"
    }

    val map = mapOf(1 to "one", 2 to "two")
    val first = map[1]
    val second = map[3]
    fun testMap() {
        first eq "one"
        second eq null
    }

    //kotlin automatically creates a null type for each class created by the programmer
    fun testAutoNullType() {
        val original: AutoNullType = AutoNullType()
        val automatic: AutoNullType? = null
    }

    private fun String.echo() {
        trace(toUpperCase())
        trace(this)
        trace(toLowerCase())
    }
    fun testStringEcho() {
        val s1: String? = "Howdy!"
        s1?.echo() //makes function safe by adding question mark
        val s2: String? = null
        s2?.echo() //function is ignored since s2 is null
        trace eq "HOWDY!\nHowdy!\nhowdy!"
    }

    fun nullableCleanCode(s:String?){
        val lengthDirty = if(s != null) s.length else null
        val lengthClean = s?.length
        lengthDirty eq lengthClean
    }
}

class AutoNullType