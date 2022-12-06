package containers

import Section
import atomicTest.eq
import containers.lists.Distributor
import containers.lists.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.Nullable
import runAll

class Lists {
    fun mutableListFunctions(): List<Int> { //this returns a MutableList, but as a List, which is not mutable
        //the object in the returned MutableList can still be edited from within this file, but
        //not with the new returned reference; additionally, the new returned reference will see
        //any changes made to the object since it references an object that is mutable elsewhere
        val readOnlyList = listOf(1, 2) //elements canNOT be added or removed
        val mutableList = mutableListOf<Int>() //elements can be modified
        mutableList.addAll(readOnlyList)
        mutableList.add(2, 3)
        mutableList += 4
        mutableList += listOf(5, 6)
        mutableList eq "[1, 2, 3, 4, 5, 6]"
        mutableList.chunked(3) eq "[[1, 2, 3], [4, 5, 6]]"
        val mutableListChunked = mutableList.chunked(3)
        mutableListChunked.flatten() eq "[1, 2, 3, 4, 5, 6]"

        return mutableList
    }

    fun inferredVsExplicit() {
        val inferredInts = listOf(1, 2, 3)
        val inferredDoubles = listOf(0.0, 1.1, 2.2)
        val inferredStrings = listOf("a", "b", "c")

        val explicitIntegers: List<Int> = listOf(1, 2, 3)
        val explicitIntegers2 = listOf<Int>(1, 2, 3)
    }

    fun iterateList() {
        val numbers = listOf(1, 2, 3)
        var result = ""
        for (i in numbers) {
            result += "$i "
        }
        result eq "1 2 3 "
    }

    fun numberListFunctions() {
        val numbers = listOf(40, 30, 20, 10)
        val listWithNulls = listOf(1, 2, null, 4, null, 6)
        numbers eq "[40, 30, 20, 10]"
        numbers[2] eq 20
        numbers.joinToString(",", "Start-", "-End") eq "Start-40,30,20,10-End"
        numbers.getOrNull(4) eq null
        numbers.sum() eq 100
        listWithNulls.filterNotNull() eq listOf(1, 2, 4, 6)
    }

    fun stringListFunctions() {
        val strings = listOf("c", "a", "b", "d", "d")
        strings.sorted() eq listOf("a", "b", "c", "d", "d") //sorted() produces new list and leaves old one alone
        strings.reversed() eq "[d, d, b, a, c]" //reversed() produces new list and leaves old one alone
        strings.first() eq "c"
        strings.take(2) eq "[c, a]"
        strings.takeLast(2) eq "[d, d]"
        strings.drop(3) eq "[d, d]"
        strings.dropLast(4) eq "[c]"
        strings.slice(1..3 step 2) eq listOf("a", "d")
        strings.distinct() eq "[c, a, b, d]"
        strings.zip(listOf("1", "2", "3")) eq listOf(Pair("c", "1"), Pair("a", "2"), Pair("b", "3"))
        listOf(10, 11, 12).zip(strings) eq "[(10, c), (11, a), (12, b)]"
        (10..100).zip(strings) eq "[(10, c), (11, a), (12, b), (13, d), (14, d)]"
        strings.zipWithNext() eq "[(c, a), (a, b), (b, d), (d, d)]"
        listOf(listOf(1, 2), listOf(3, 4)).flatten() eq listOf(1, 2, 3, 4)
    }

    fun lambdaFunctions() {
        val numbers = listOf(1, 2, 2, 3)
        val strings = listOf("This", "is", "a", "list.")
        var total = 0
        numbers.map { it * 2 } eq listOf(2, 4, 4, 6)
        numbers.mapIndexed { index, i -> index * i } eq listOf(0, 2, 4, 9)
        numbers.joinToString(" ") { "*$it*" } eq "*1* *2* *2* *3*"
        numbers.none { it == 2 } eq false
        numbers.any { it == 2 } eq true //stops at first match
        numbers.all { it == 2 } eq false
        numbers.find { it == 2 } eq 2 //stops at first match
        numbers.forEach { total += it }
        total eq 8
        strings.filter { it.length > 2 } eq listOf("This", "list.")
        strings.filterIndexed { index, _ -> index % 2 == 0 } eq listOf("This", "a")
        strings.filterNot { it.length > 2 } eq listOf("is", "a")
        strings.count { it != "a" } eq 3
        strings.partition { it.length > 2 } eq Pair(listOf("This", "list."), listOf("is", "a"))
        val (truly, falsely) = strings.partition { it.length > 2 }
        truly eq listOf("This", "list.")
        falsely eq listOf("is", "a")
        strings.takeLastWhile { it.length > 2 } eq listOf("list.")
        strings.dropWhile { it.length > 2 } eq listOf("is", "a", "list.")
        listOf(10, 11, 12).zip(strings) { x, y -> Pair(y, x) } eq "[(This, 10), (is, 11), (a, 12)]" //swap Pair
        val range = 1..2
        range.map { a -> range.map { b -> a to b } } eq
                listOf(listOf(Pair(1, 1), Pair(1, 2)), listOf(Pair(2, 1), Pair(2, 2)))
        range.map { a -> range.map { b -> a to b } }.flatten() eq
                listOf(Pair(1, 1), Pair(1, 2), Pair(2, 1), Pair(2, 2))
        range.flatMap { a -> range.map { b -> a to b } } eq
                listOf(Pair(1, 1), Pair(1, 2), Pair(2, 1), Pair(2, 2))
    }

    data class SomeObject(val nullableField: String?)

    private val store = Distributor("Lounge", 1949)
    private val online = Distributor("Speedster", 1988)
    private val supermarket = Distributor("Speedy", 2001)

    private val tv = Product("TV", "Medium quality", 899.95, 12, 2100, true, listOf(online, supermarket))
    private val couch = Product("couch", null, 549.86, 5, 2050, true, listOf(store))
    private val desk = Product("desk", "Low quality", 480.35, 5, null, false, listOf(store))
    private val laptop = Product("laptop", null, 949.95, 10, null, false, listOf(supermarket))

    private fun nonComparableElements() {
        val list = listOf(tv, couch, desk, laptop)
        list.sumBy { it.inventory } eq 30
        list.sumByDouble { it.price } eq 2880.11
        list.sortedBy { it.name } eq listOf(tv, couch, desk, laptop) //from 'A'..'Z', then 'a'..'z'
        list.sortedByDescending { it.name } eq listOf(laptop, desk, couch, tv) //starting with 'z'
        list.minByOrNull { it.price } eq desk
    }

    fun memberReferences() {
        val productList = listOf(tv, couch, desk, laptop)
        productList.filterNot { it.onSale } eq listOf(desk, laptop) //uses lambda
        //PROPERTY REFERENCE:
        productList.filterNot(Product::onSale) eq listOf(desk, laptop) //uses member reference
        //compareBy() sorts false before true
        productList.sortedWith(compareBy(Product::onSale, Product::name)) eq listOf(desk, laptop, tv, couch)
        productList.sortedWith(
            compareByDescending(Product::inventory)
                .then(compareBy(Product::name, Product::price))
        ) eq
                listOf(tv, laptop, couch, desk)
        fun Product.functionReference(): Boolean =
            price < 900.00 || distributors.any { it.name.contains("Speed") && it.estYear > 2000 }
        //FUNCTION REFERENCE:
        productList.any(Product::functionReference) eq true //takes function reference to extension function above as argument
        //TOP-LEVEL FUNCTION REFERENCE
        productList.any(::topLevelFunction) eq false //don't need class name for top-level function, since it has no class
        val studentNames = listOf("Bob", "Tom")
        val studentList = studentNames.mapIndexed { index, s -> Student(index, s) }
        studentList eq listOf(Student(0, "Bob"), Student(1, "Tom"))
        //CONSTRUCTOR REFERENCE:
        studentList eq studentNames.mapIndexed(::Student) //the Student constructors are referenced in as parameters
        //EXTENSION FUNCTION REFERENCE:
        //below, "function" is any function that takes an Int argument and produces an Int
        fun mathematics(one: Int, function: (Int) -> Int) =
            function(one) //must have parentheses in parameter around Int
        mathematics(2, Int::times50) eq 100
        mathematics(2, Int::plus10) eq 12
        fun listToString(list: List<String>, function: (List<String>) -> String) = function(list)
        listToString(studentNames, List<String>::toStarString) eq "*Bob*Tom*"
    }

    fun listReturnIsInferred() = listOf(1, 2)
    fun listReturnIsExplicit(): List<Int> = listOf(1, 2)

    fun constructLists() {
        //List() and MutableList() are not constructors, but functions
        //their names begin with capitol letters to make them look like constructors
        val list1 = List(4, { it })
        list1 eq listOf(0, 1, 2, 3)

        val list2 = List(4) { 0 }
        list2 eq listOf(0, 0, 0, 0)

        val list3 = List(5) { 'a' + it }
        list3 eq listOf('a', 'b', 'c', 'd', 'e')

        val list4 = List(5) { list3[it % 3] }
        list4 eq listOf('a', 'b', 'c', 'a', 'b')

        val lambdaInParentheses = MutableList(5, { 10 * (it + 1) })
        val lambdaOutsideParentheses = MutableList(5) { 10 * (it + 1) }
        lambdaInParentheses eq lambdaOutsideParentheses
    }

    init {
        runAll(
            "List Examples",
        )
    }
}

fun topLevelFunction(product: Product) = product.name.contains("w")

data class Student(
    val id: Int,
    val name: String,
)

fun Int.times50() = times(50)
fun Int.plus10() = plus(10)
fun List<String>.toStarString() = joinToString("*", "*", "*")