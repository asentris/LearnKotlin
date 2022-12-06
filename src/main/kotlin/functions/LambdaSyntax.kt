    package functions

import Section
import atomicTest.eq
import atomicTest.neq
import runAll

class LambdaSyntax {
    //lambda is a literal function, meaning it is not declared, but passed in as an expression
    //"return" from inside a lambda will exit the containing function defined with "fun", not the lambda itself
    //lambdas are defined by:
    //1. always surrounded by: { }
    //2. the input parameter types (these are optional if they can be inferred)
    //3. an arrow: ->
    //4. the body

    //p: stands for parameter(s) is(are) of type: ...
    //r: stands for return value is of type: ...

    private fun lambdaAsLiteral() {
        { a: Int, b: Int -> a + b }.invoke(1, 2) eq 3
        { a: Int, b: Int -> a + b }.invoke(1, 2) eq 1 + 2
        { a: Int, b: Int -> a > b }(1, 2) eq false
        { a: Int, b: Int -> a > b }(1, 2) eq (1 > 2)
    }

    private fun fullSyntax() {
        //p: Int, Char; r: String; one == first; two == second
        //first and second are used to document the meaning of these parameters for future reference
        val fullSyntax: (first: Int, second: Char) -> String = { one: Int, two: Char -> "$one, $two" }
        fullSyntax(1, 'a') eq "1, a"
    }

    private fun syntaxSemiStripped() {
        //p: Int, Char; r: String; one == parameter 1; two == parameter 2
        val syntaxSemiStripped: (Int, Char) -> String = { one, two -> "$one, $two" }
        syntaxSemiStripped(1, 'a') eq "1, a"
    }

    private fun oneParameterSyntax() {
        val oneParameterRegular: (Int) -> String = { single -> "$single" } //p: Int, r: String, single == parameter
        val oneParameterAlternate: (Int) -> String = { "$it" } //p: Int, r: String, it == parameter
        oneParameterRegular(2) eq "2"
        oneParameterAlternate(2) eq "2"
    }

    private fun inferredReturnTypeSyntax() {
        //variable and return type are inferred; lambda must explicitly declare named parameter with type
        val simpleInferredReturn = { single: Int -> single * 10 } //p: Int; r: Int; single == parameter
        val lastLineInferred = { single: Int ->
            var sum = single
            sum += 100
            sum
        } //p: Int; r: Int; single == parameter
        val multipleInferredReturns = { single: Int ->
            when (single) {
                in 0..9 -> "1"
                in 10..99 -> 2
                in 100..999 -> 3.0
                else -> false
            }
        } //p: Int; r: String, Int, Double, Boolean; single == parameter

        simpleInferredReturn(2) eq 20
        multipleInferredReturns(0) eq "1"
        multipleInferredReturns(-1) eq false
        lastLineInferred(1) eq 101
    }

    private fun fullyStrippedSyntax() {
        val fullyStrippedSyntax = { -> } //p: (); r: Unit
        val fullyStrippedSyntaxAlternate = { } //p: (); r: Unit
        fullyStrippedSyntax() eq Unit
        fullyStrippedSyntaxAlternate neq { }
    }

    private fun destructuring() {
        //destructuring declarations syntax can be used for lambda parameters
        //if a lambda has a parameter of the Pair type, Map.Entry, or any other type that
        //has the appropriate componentN functions, several new parameters can be
        //introduced instead of one by putting them in parentheses:
        val map = mutableMapOf(1 to 1, 2 to 2, 3 to 3, 4 to 4)
        map.mapValues { entry -> entry.value * 2 } eq mapOf(1 to 2, 2 to 4, 3 to 6, 4 to 8)
        map.mapValues { (key, value) -> value + 3 } eq mapOf(1 to 4, 2 to 5, 3 to 6, 4 to 7)
        map.mapValues { (_, value) -> value + 3 } eq mapOf(1 to 4, 2 to 5, 3 to 6, 4 to 7)

        val one = { a: Int -> a }                                       // one parameter
        val two = { a: Int, b: Int -> a + b }                           // two parameters
        val oneDes = { (a, b): Pair<Int, Int> -> a + b }                // a destructured pair
        val twoDes = { (a, b): Pair<Int, Int>, c: Int -> a + b + c }    // a destructured pair and another parameter

        //specifying the type for the whole destructured parameter or for a specific component separately:
        map.mapValues { (_, value): Map.Entry<Int, Int> -> value } eq mutableMapOf(1 to 1, 2 to 2, 3 to 3, 4 to 4)
        map.mapValues { (_, value: Int) -> value } eq mutableMapOf(1 to 1, 2 to 2, 3 to 3, 4 to 4)
    }

    private fun trailingLambdas() {
        //a lambda can be removed from parentheses if it is the last argument
        val inParentheses = MutableList(5, { 10 * (it + 1) })
        val outsideParentheses = MutableList(5) { 10 * (it + 1) }
        val onlyOneArgument = run({ println("inside parentheses") })
        val onlyOneArgumentAlternate = run { println("outside parentheses") }
        inParentheses eq listOf(10, 20, 30, 40, 50)
        inParentheses eq outsideParentheses
    }

    private fun nullableLambda() {
        //enclose the function type to declare it as nullable with the '?'
        //***NEED to double check this usefulness???***
        val nullableType: ((Int) -> String)? = { "???" }

        if (nullableType != null) {
            nullableType(1)
        } else {
            null
        } eq "???"
    }

    private fun parentheses() {
        var noEnclosure: (Int) -> (Int) -> Unit = { a -> { Unit } }
        noEnclosure = { { } }
        val enclosedReturn: (Int) -> ((Int) -> Unit) = noEnclosure
        noEnclosure eq enclosedReturn

        val enclosedParameter: ((Int) -> Int) -> Unit = { }
        val enclosedParameterAlternate: ((Int) -> (Int)) -> Unit = { }
        enclosedParameter neq enclosedParameterAlternate
        enclosedParameter neq enclosedReturn

        val myTypeAlias: myOwnName = enclosedParameter
        myTypeAlias eq enclosedParameter
    }

    private fun passingValues() {
        val list = listOf(1, 2, 3, 4, 5, 6)

        //accepts only Ints
        //if index is not present, result of (i * a) becomes "it"
        fun List<Int>.passInt(a: Int, i: Int): Int =
            this.getOrElse(i * a) { it * a }

        //accepts function parameter
        //if a == 6 && f == {it * 2}
        //f(2 * a) becomes f(12) which passes to {it * 2}; this becomes {24}; if this index isn't present,
        //it is passed to {it * a}, which results in 144
        fun List<Int>.passFunction(a: Int, f: (Int) -> Int): Int =
            this.getOrElse(f(2 * a)) { it * a }

        //this expects the calling function to make the initial calculation
        fun List<Int>.passIntAlternate(a: Int, f: (Int) -> Int): Int =
            this.getOrElse(f(a)) { it * a }

        list.passInt(1, 2) eq 3
        list.passInt(6, 2) eq 72
        list.passFunction(1) { it * 2 } eq 5
        list.passFunction(6) { it * 2 } eq 144
        list.passInt(143, 4967) eq list.passIntAlternate(143) { it * 4967 }
    }

    init {
        runAll(
            "Lambda function syntax",
            Section("Lambda with function called directly upon it", ::lambdaAsLiteral),
            Section("Full lambda expression syntax", ::fullSyntax),
            Section("Syntax with some info stripped", ::syntaxSemiStripped),
            Section("Syntax with one parameter", ::oneParameterSyntax),
            Section("Syntax with return types inferred", ::inferredReturnTypeSyntax),
            Section("Fully stripped syntax", ::fullyStrippedSyntax),
            Section("Destructuring lambdas", ::destructuring),
            Section("Trailing lambdas", ::trailingLambdas),
            Section("Nullable lambdas", ::nullableLambda),
            Section("How parentheses are applied", ::parentheses),
            Section("Passing values", ::passingValues),
        )
    }
}

typealias myOwnName = ((Int) -> (Int)) -> Unit //used in parentheses()