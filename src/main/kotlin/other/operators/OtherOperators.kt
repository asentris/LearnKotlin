package other.operators

import Section
import atomicTest.eq
import runAll

class OtherOperators {

    private fun rangeOperator() { // ..
        1..4 eq IntRange(1, 4)              //Int
        1L..4L eq LongRange(1, 4)           //Long
        'a'..'d' eq CharRange('a', 'd')     //Char
        'a'..'d' eq 'a'.rangeTo('d')  //Char
        1.0..4.0 eq 1.0.rangeTo(4.0)   //Double
        1F..4F eq 1F.rangeTo(4F)       //Float
    }

    private fun typeDeclarations() { // :
        val a: Int = 1 //separates name from its declared type
        a::class eq Int::class
    }

    private fun arrow() { // ->
        "abc".filter { char -> char == 'b' } eq "b" //lambda - separates parameters & body
        fun myToString(i: Int): (Int) -> String = { a -> a.toString() } //function - separates parameters & return type
        when (1) { //when - separates the condition & body
            1 -> 1 eq 1
        }
    }

    private fun label() { // @
        this@OtherOperators::class eq OtherOperators::class
        //see BreakContinue.kt & Labels.kt
    }

    private fun semiColon() { // ;
        1 eq 1; 2 eq 2
    }

    private fun stringOperators() { // $, ${}, etc.
        val a = 1
        "- $a -" eq "- 1 -"
        "- ${a + a} -" eq "- 2 -"

        "ab\bc".toList() eq listOf('a', 'b', '\b', 'c')
        println("ab\bc")
        "new\nline" eq "new\nline"
        "remove\rkeep" eq "\rkeep"
        "insert\ttab" eq "insert\ttab"
        "\uFF00" eq "\uFF00"

        "- \' -" eq """- ' -"""
        "- \" -" eq """- " -"""
        "- \$ -" eq """- $ -"""
        "- \\ -" eq """- \ -"""
    }

    private fun parameterSub() { // _
        "abc".filter { _ -> true } //substitute unused lambda parameter
        val myFilter: (Int) -> Boolean = fun(_): Boolean = true //substitute unused destructuring declaration parameter
    }

    init {
        runAll(
            "Uncategorized operators",
            Section("Range operator", ::rangeOperator),
            Section("Type declaration operator", ::typeDeclarations),
            Section("Arrow operator", ::arrow),
            Section("Label operator", ::label),
            Section("Separate functions on same line", ::semiColon),
            Section("String operators", ::stringOperators),
            Section("Parameter substitution operators", ::parameterSub),
        )
    }
}
