package functions

import Section
import atomicTest.eq
import atomicTest.neq
import dataTypes.GenericTypes
import runAll

//a function type is defined by:
//1. the input parameters types between parentheses, ex: (Int, Double)
//2. an arrow: ->
//3. the return type, ex: String
class FunctionsSavedAs {

    private fun functions() { //examples of functions with their type
        fun equalTo(a: Int, b: Int): Boolean = a == b //(Int, Int) -> Boolean
        fun equalToInferred(a: Int, b: Int) = a == b //(Int, Int) -> Boolean
        fun printReturnUnit() {
            println("return unit")
        } //() -> Unit

        fun Int.greaterThan(b: Int): Boolean = this > b //Int.(Int) -> Boolean

        equalTo(1, 2) eq false
        equalToInferred(1, 1) eq true
        printReturnUnit() eq Unit
        10.greaterThan(20) eq false
    }

    private fun functionReturns() { //examples of returns with a function type
        fun empty(): () -> Unit = { -> } //() -> Unit
        fun allEqual(a: Int): (Int, Int) -> Boolean = { b, c -> a == b && b == c } //(Int, Int) -> Boolean
        fun allEqualInferred(a: Int) = { b: Int, c: Int -> a == b && b == c } //(Int, Int) -> Boolean

        fun returnBoolean(x: Int, y: Int, f: (Int, Int) -> Boolean) = f(x, y)
        returnBoolean(1, 2, allEqual(1)) eq false
        returnBoolean(1, 1, allEqualInferred(1)) eq true
    }

    private fun variables() { //examples of variables with a function type
        val empty: () -> Unit = { } //() -> Unit, empty parameter list, return type: Unit
        val equalTo: (Int, Int) -> Boolean = { a, b -> a == b } //(Int, Int) -> Boolean
        val equalToInferred = { a: Int, b: Int -> a == b } //(Int, Int) -> Boolean

        empty() eq Unit
        equalTo(1, 1) eq true
        equalTo.invoke(1, 1) eq true //alternate way to call
        equalToInferred(1, 2) eq false

        //alternate way to save function as a variable by using a class extension:
        val classExtension: String.(Int) -> String = { this + it } //String.(Int) -> String
        val saveFunction: (String, Int) -> String = classExtension

        saveFunction("A", 1) eq "A1"
    }

    private fun anonymousFunctions() {
        //anonymous functions allow declaration of return type, while lambdas don't
        //"return" from inside an anonymous function will exit its own "fun" declaration"

        fun savedAsVariables() {
            val anonymousFunRegular: (Int, Int, Int) -> String = fun(a, b, _): String = "* $a $b *"
            val anonymousFunInferred = fun(a: Int): String { return "* $a *" }
            val anonymousFunExtension = fun Int.(other: Char): String = ". $this $other ."

            anonymousFunRegular(1, 2, 3) eq "* 1 2 *"
            (fun(a: Int, b: Int, _: Int): String = "* $a $b *")(1, 2, 3) eq "* 1 2 *"
            anonymousFunInferred(2) eq "* 2 *"
            1.anonymousFunExtension('A') eq ". 1 A ."
        }

        fun usedInFunctions() {
            fun notAnonymousFull(): () -> String {
                fun name(): String {
                    return "Text"
                }
                return ::name
            }

            fun notAnonymousShort(): () -> String {
                fun name() = "Text"
                return ::name
            }

            fun fullSyntax(): () -> String {
                return fun(): String { return "Text" }
            }

            fun partSyntax(): () -> String {
                return fun() = "Text"
            }

            fun stripSyntax() = fun() = "Text"

            //notAnonymousFull() eq "Text"
            //notAnonymousShort() eq "Text"
            fullSyntax() neq fun() = "Text" //different objects, but function is still the same
            partSyntax() neq fullSyntax()
            stripSyntax() neq partSyntax()

            //since fullSyntax() returns a function, that function can be called by appending
            //the argument list to fullSyntax(); in this case there are no parameters: ()
            fullSyntax()() eq "Text"
        }

        fun savedAsFunctions() {
            fun myCounter(): Pair<() -> Unit, () -> Int> {
                var counter = 0
                fun inc() = fun() { counter += 1 }
                fun value() = fun() = counter
                return inc() to value()
            }

            fun myCounterShort(): Pair<() -> Unit, () -> Int> {
                var counter = 0
                fun inc() {
                    counter += 1
                }

                fun value() = counter
                return Pair(::inc, ::value)
            }
        }

        fun savedInExtensions() {
            val map = mapOf(1 to 'A', 2 to 'B', 3 to 'C')

            map.any(
                fun(a: Map.Entry<Any, Any>): Boolean {
                    return when (a.key) {
                        1 -> true
                        else -> false
                    }
                }
            )
            map.any()
        }

        savedAsVariables()
        usedInFunctions()
        savedInExtensions()
    }

    private fun classExtensions() {
        //the receiver object is the class before the '.' and the parameter is within the parentheses
        val withObject: String.(Int) -> String = { this + it } //String.(Int) -> String
        val withReceiver: Int.(a: Char) -> String = { "$this $it" } //Int.(Char) -> String
        val saveFunction: (String, Int) -> String = withObject

        "Obe".withObject(1) eq "Obe1"
        withObject("Obe", 1) eq "Obe1" //alternate way to call
        withObject.invoke("Obe", 1) eq "Obe1" //alternate way to call

        1.withReceiver('A') eq "1 A"
        withReceiver(1, 'A') eq "1 A"
        withReceiver.invoke(1, 'A') eq "1 A"
    }

    private fun classExtensionReturns() {
        fun Int.allEqual(a: Int): (Int, Int) -> Boolean = { b: Int, c: Int -> a == b && b == c && c == this }
        fun Int.allEqualInferred(a: Int) = { b: Int, c: Int -> a == b && b == c && c == this }

        fun returnBoolean(x: Int, y: Int, f: (Int, Int) -> Boolean) = f(x, y)
        returnBoolean(1, 1, 2.allEqual(1)) eq false
        returnBoolean(1, 1, (Int::allEqual)(2, 1)) eq false //alternate way to call function
        returnBoolean(1, 1, 1.allEqualInferred(1)) eq true
    }

    private class ClassReturns(val f2: (Int, Int) -> Boolean) : (Int, Int) -> Boolean {
        override operator fun invoke(aa: Int, bb: Int): Boolean = aa == bb

        fun memberFunction(aa2: Int, bb2: Int): Boolean = aa2 == bb2 //used only in parameters()
    }

    private fun parameters() { //examples of parameters with a function type
        fun returnBoolean(x: Int, y: Int, f: (Int, Int) -> Boolean) = f(x, y) //parameter 'f': (Int, Int)-> Boolean

        fun functions(a2: Int, b2: Int) = a2 == b2
        fun functionReturn() = { a3: Int, b3: Int -> a3 == b3 }
        val variables = { a4: Int, b4: Int -> a4 == b4 }
        val anonymousFunctions = fun(a5: Int, b5: Int): Boolean = a5 == b5
        val classExtensions: Int.(Int) -> Boolean = { this == it } //String.(Int) -> String
        fun Int.classExtensionReturns(): (Int, Int) -> Boolean = { a6: Int, b6: Int -> a6 == b6 }
        returnBoolean(1, 1) { a, b -> a == b } eq true //pass function in with lambda
        returnBoolean(1, 2, ::functions) eq false //pass function in with "::" to signify function reference
        returnBoolean(1, 1, functionReturn()) eq true //pass function in with function that returns function
        returnBoolean(1, 2, variables) eq false //pass function in with variable of type function
        returnBoolean(1, 1, anonymousFunctions) eq true //pass function in with anonymous function
        returnBoolean(1, 2, classExtensions) eq false //pass function in with class extension
        returnBoolean(1, 1, 2.classExtensionReturns()) eq true //pass function in with return for class extension
        returnBoolean(1, 1, ClassReturns(variables).f2) eq true//pass function in with class constructor
        returnBoolean(1, 1, ClassReturns(variables)) eq true //pass function in with overridden invoke method for class
        returnBoolean(1, 1, ClassReturns(variables)::memberFunction) eq true //pass function in with class function
    }

    init {
        runAll(
            "Functions can be saved as",
            Section("Regular functions", ::functions),
            Section("Function returns", ::functionReturns),
            Section("Variables", ::variables),
            Section("Anonymous functions", ::anonymousFunctions),
            Section("Class extensions", ::classExtensions),
            Section("Class extension returns", ::classExtensionReturns),
            Section("Parameters", ::parameters),
        )
    }
}