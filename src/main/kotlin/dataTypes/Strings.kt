package dataTypes

import atomicTest.eq

class StringsType {
    fun strings() {
        val example = 2
        "Number: $example" eq "Number: 2"
        "Solved expression: ${example + 2}" eq "Solved expression: 4"
        "New\nLine" eq "New\nLine"
        "Insert\tTab" eq "Insert\tTab"
        "Text " + "1" eq "Text 1"
        """Include "" quotes""" eq "Include \"\" quotes"
        " a b\nc ".trim() eq "a b\nc" //removes leading and trailing spaces
    }

    fun stringSplit() {
        //.split() saves string as List and divides it into the argument provided
        //the argument is a Regex object that creates a regular expression
        //\W is a special character that means "not a word character"
        //+ means "one or more of the preceding", so it breaks at one or more non word characters
        "abc, def. ghi \njkl".split(Regex("\\W+")) eq "[abc, def, ghi, jkl]"
        val synergiesString = "{out} Hero && Valiant || {in} Common && Squad || {in}{power} Excellent Aim"
        val synergiesInitialSplit: List<String> = synergiesString.split("||", ignoreCase = true).map { it.trim() }
        synergiesInitialSplit eq "[{out} Hero && Valiant, {in} Common && Squad, {in}{power} Excellent Aim]"
        val synergiesThatHelp: List<String> = synergiesInitialSplit.filter { it.contains("{in}", true) }
            .map { it.substringAfterLast("}").trim() }
        synergiesThatHelp eq "[Common && Squad, Excellent Aim]"
        val synergyPowersThatHelp: List<String> =
            synergiesInitialSplit.filter { it.contains("{in}", true) && it.contains("{power}", true) }
                .map { it.substringAfterLast("}").trim() }
        synergyPowersThatHelp eq "[Excellent Aim]"

    }

    fun stringTrimMargin() {
        " \n  -->Line 1\n  -->Line 2 \n ".trimMargin() eq "-->Line 1\n  -->Line 2"
        "     -->Line 1\n  -->Line 2    ".trimMargin() eq "-->Line 1\n  -->Line 2"
        "  |  -->Line 1\n |-->Line 2    ".trimMargin() eq "-->Line 1\n-->Line 2"
        "  |  -->Line 1 \n |-->Line 2   ".trimMargin("|-->") eq "|  -->Line 1 \nLine 2"
    }

    fun isNullOr() {
        val a: String? = null
        a.isNullOrEmpty() eq true
        a.isNullOrBlank() eq true
        val b: String? = ""
        b.isNullOrEmpty() eq true
        b.isNullOrBlank() eq true
        val c: String? = " \t\n"
        c.isNullOrEmpty() eq false
        c.isNullOrBlank() eq true
    }
}