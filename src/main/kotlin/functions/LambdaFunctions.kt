package functions

import Section
import atomicTest.eq
import runAll

class LambdaFunctions {

    //lambda expressions and anonymous functions are function literals
    //function literals are functions that are not declared but passed immediately as an expression
    //a return from inside a lambda will return from the enclosing function keyword fun

    fun passIntoFunction() {
        fun List<Int>.hasValueThatConvertsTo1Double(expression: (Double) -> String): Boolean {
            for (i in this) { //i is the Int in the List: this
                if (expression(i.toDouble()) == "1.0") //the expression expects a Double, so 'i' is converted
                //the expression expects to result in a string, so a String must be input into the lambda
                    return true //return value is Boolean
            }
            return false
        }

        val ints = listOf(1, 2, 3)
        ints.hasValueThatConvertsTo1Double { "$it" } eq "true"
        ints.hasValueThatConvertsTo1Double { "${it - 3}" } eq "false"

        /** ABOVE EXPLAINED:
         * The list of integers are immediately sent to the extension function
         * "this" is the List<Int> & each Integer can be extracted with for(i in this)
         * By using expression(), you are asking for the lambda function to be used and injecting the Double
         * Since expression() takes a Double parameter, "i" must first be converted to a Double
         * The lambda then takes that Double and performs its function on it and returns that value
         * The converted value in this case is a String, which is then compared to the string in the if statement
         * The extension function returns a Boolean, so that is the final value
         */

        fun <T> List<T>.anyAlternate( //defined as  extension to generic list to accept any type
            predicate: (T) -> Boolean
        ): Boolean { //predicate function is callable with generic type
            for (element in this) { //elements are of generic type in the list: this
                if (predicate(element)) //input is the result of the argument declared by the user (example: it > 0)
                    return true //the return type for the function itself
            }
            return false
        }

        val doubles = listOf(1.0, 2.0, 3.0)
        val strings = listOf("a", "b", " ")
        doubles.anyAlternate { it > 0.0 } eq true
        strings.anyAlternate { it.isBlank() } eq true
        strings.anyAlternate(String::isNotBlank) eq true
        doubles.any { it > 0 }

        fun repeatX2Alternate(times: Int, action: (Int) -> Unit) {
            for (i in 1 until times) {
                action(i * 2)
            }
        }
        repeatX2Alternate(3) { println("Print me: $it") }

        fun <T, R : Any> Iterable<T>.mapIndexedNotNull(transform: (Int, T) -> R?): List<R> {
            val list = mutableListOf<R>()
            for ((index, e) in withIndex()) { //OR this.withIndex
                val transformed = transform(index, e)
                if (transformed != null) {
                    list += transformed
                }
            }
            return list
        }

        val list = listOf("a", "b", "c", "d")
        list.mapIndexedNotNull { index, s -> if (index % 2 == 0) "$s!" else null } eq listOf("a!", "c!")
    }

    fun example() {
        val intList = listOf(1, 2)
        val stringList = listOf("ab", "cd")

        val explicitParameter = intList.map({ n: Int -> "[$n]" }) //parameter list is first, then the function body
        val implicitParameter = intList.map({ n -> "[$n]" })

        //can remove parentheses when lambda is the only function argument or the last argument
        val moreThanOneParameter = intList.mapIndexed { index, number -> "[$index: $number]" }
        val ignoreParameter = intList.mapIndexed { index, _ -> "[$index]" } //ignore with underscore
        val singleParameter = intList.map { it * 2 } //kotlin auto generates "it" as the single parameter

        val oneArgument = stringList.map { it.reversed() }
        //the lambda below is the last argument, all others are placed within parentheses
        val moreThanOneArgument = intList.joinToString("/", "Start: ", ".") { "[$it]" }
        //below makes the lambda a named argument that is included within joinToString()
        val namedArgument = intList.joinToString("/", "Start: ", ".", transform = { "[$it]" })
        val noArgument = run { -> "NONE" } //arrow can also be removed

        val saveLambda = { x: Int -> x % 2 == 0 } //lambdas can be saved as val or var

        explicitParameter eq listOf("[1]", "[2]")
        implicitParameter eq listOf("[1]", "[2]")

        moreThanOneParameter eq listOf("[0: 1]", "[1: 2]")
        ignoreParameter eq listOf("[0]", "[1]")
        singleParameter eq listOf(2, 4)

        oneArgument eq listOf("ba", "dc")
        moreThanOneArgument eq "Start: [1]/[2]."
        namedArgument eq "Start: [1]/[2]."
        noArgument eq "NONE"

        intList.filter { it == 1 } eq listOf(1)
    }

    private fun mapIndexedAndZipExample() {
        //combine list elements with their index into a list of pairs
        //this has two functions that produce the same result different ways
        fun <T> List<T>.zipWithIndexV1(): List<Pair<T, Int>> = mapIndexed { index, t -> Pair(t, index) }
        fun <T> List<T>.zipWithIndexV2(): List<Pair<T, Int>> = zip(indices)

        listOf('a', 'b', 'c').zipWithIndexV1() eq "[(a, 0), (b, 1), (c, 2)]"
        listOf('a', 'b', 'c').zipWithIndexV2() eq "[(a, 0), (b, 1), (c, 2)]"
    }

    private fun diggingDeeperWithIt() {
        //finds friends of friends who aren't already friends or initial person
        //uses flatMap on the friends list in Person object to iterate through that list with "it"
        class Person(val name: String) {
            val friends: MutableList<Person> = mutableListOf()

            override fun toString() = "($name, friends: ${friends.map { it.name }})"
        }

        fun friendSuggestions(person: Person): Set<Person> {
            return person.friends.flatMap { it.friends }.toSet() -
                    person.friends - person
        }

        val alice = Person("Alice")
        val bob = Person("Bob")
        val charlie = Person("Charlie")
        alice.friends += bob
        bob.friends += alice
        bob.friends += charlie
        charlie.friends += bob

        friendSuggestions(alice) eq setOf(charlie)
    }

    private fun flattenFlatMapExample() {
        //flattens list using map and flatten
        fun <T, R> List<T>.myFlatMap(f: (T) -> List<R>): List<R> =
            map(f).flatten()

        listOf(3, 1, 4).myFlatMap { (0..it).toList() } eq
                listOf(0, 1, 2, 3, 0, 1, 0, 1, 2, 3, 4)
    }

    private fun flatMapFlattenExample() {
        //flattens list of lists with flatMap
        fun <T> List<List<T>>.myFlatten(): List<T> =
            flatMap { it }

        val listOfLists = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
        listOfLists.myFlatten() eq listOf(1, 2, 3, 4, 5, 6)
    }

    private fun flatMapFilterExample() {
        //filters list using flatMap
        fun <T> List<T>.myFilter(f: (T) -> Boolean): List<T> =
            flatMap { if (f(it)) listOf(it) else emptyList() }

        listOf(1, 12, 22, 31).myFilter { it.toString().contains("2") } eq listOf(12, 22)
    }

    private fun closure() {
        //lambdas can access their closure, which includes elements defined in the outer scope
        //these elements can be modifies from within the lambda
        val list = listOf(1, 2, 3, 4)
        var sum = 0 //will be modified
        val divider = 2
        list.filter { it % divider == 0 }.forEach { sum += it }
        sum eq 6
        //rewritten:
        list.filter { it % divider == 0 }.sum() eq 6
    }

    init {
        runAll(
            "Lambda function examples",
            Section("Using mapIndexed and zip to combine elements with their index", ::mapIndexedAndZipExample),
            Section("""Using "it" deeper in object instance""", ::diggingDeeperWithIt),
            Section("Using map and flatten to implement flapMap", ::flattenFlatMapExample),
            Section("Using flatMap to implement flatten", ::flatMapFlattenExample),
            Section("Using flatmap to implement filter", ::flatMapFilterExample),
            Section("Capture and modify", ::closure),
        )
    }
}