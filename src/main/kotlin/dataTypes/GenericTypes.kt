package dataTypes

import Section
import atomicTest.eq
import atomicTest.neq
import runAll

//<> = angle brackets are used to define a generic type
//<T> = the generic type is represented by the given placeholder 'T'
class GenericTypes {
    private fun defineGenericConstructor() {
        class HasGenConstr<T>(private val genConstr: T) {
            fun getGenConstr(): T = genConstr
        }

        data class SpecificConstr(private val specificConstr: String) {
        } //the data class ensures that the two different objects tested below are "eq"

        val holderInt = HasGenConstr(1)
        val holderObject = HasGenConstr(SpecificConstr("Hello"))

        holderInt.getGenConstr() eq 1
        holderObject.getGenConstr() eq SpecificConstr("Hello")

        holderInt.getGenConstr()::class eq Int::class
        holderObject::class eq HasGenConstr::class
    }

    private fun anyHolder() {
        class Dog {
            fun bark() = println("Ruff!")
        }

        //the Any type can be used for almost anything, but is limited since it removes the type references for objects:
        class AnyConstructor(private val anyConstr: Any) {
            fun getAnyConstr(): Any = anyConstr
        }

        val holder = AnyConstructor(Dog())
        val fakeDog = holder.getAnyConstr()
        val realDog = Dog()
        fakeDog::class eq realDog::class //class remains the same
        fakeDog::class.isInstance(Dog()) eq realDog::class.isInstance(Dog()) //both are instances of Dog()
        fakeDog::hashCode neq realDog::hashCode //fakeDog becomes type Any() and properties are stripped
        //dog.bark() = won't compile since any is assigned as an Any, not a Dog and can't bark()
        realDog.bark()
    }

    private fun starProjection() {
        //a star projection removes all type info (called "type erasure") for the object passed in
        val gary = Person("Gary", 30)
        val list: List<*> = listOf(gary)
        list[0] eq gary
        list[0]!!::class eq gary::class //class remains the same
        list[0]::hashCode neq gary::hashCode //list[0] becomes Any? and properties are stripped
    }

    private fun getOrPutExamples() {
        val bill = Person("Bill", 20)
        val chad = Person("Chad", 20)
        val devin = Person("Devin", 24)
        val dustin = Person("Dustin", 30)
        val personList = listOf(bill, chad, devin, dustin)
        val mapByAge = personList.groupBy { it.age }
        val mapAllByThis = personList.groupBy { 20 }

        //goto Maps.kt for explanation of getOrPut()
        //T is declared as the type that is contained in the given List()
        //T is automatically assigned to the variable "it" (used only in lambda) when there is only one parameter
        //R is declared as the result of the function within the parentheses of "f2: (T)"
        //this means that the result of the function called by the user is assigned to R
        //the calling code therefore decides what the result of the lambda expression (or other) will be
        //T in "f2: (T) -> R" means that the input value within the body of the function when f2() is called
        //must be the same type as that which is declared by the calling code
        //because of this, a specific type cannot be passed into f2(T), but rather the generic type T must
        //still be passed in
        //"element" is of type T, since "this" is a List<T>, therefore, specific properties can't be directly
        //retrieved from "element" and it must first be sent to the calling code to retrieve that data
        fun <T, R> List<T>.groupByAge(f1: Int, f2: (T) -> R): Map<R, List<T>> {
            val result = mutableMapOf<R, MutableList<T>>()
            for (element in this) {
                val reifiedd = element as Person
                //println(reifiedd.name)
                //println(element)
                //println(this)
                //R is required in getOrPut because "result" must take 'R' as its key
                //'T' is explicitly declared for "result" in the initialization and need not be stated in "list ="
                val list = result.getOrPut(f2(element)) { mutableListOf() }
                list.add(element)
            }
            return result
        }

        //(Int) -> String   -- test it out
        fun <T> List<T>.groupByAge2(f: (T) -> Int): Map<Int, List<T>> {
            val result = mutableMapOf<Int, MutableList<T>>()
            for (element in this) {
                val list = result.getOrPut(f(element)) { mutableListOf() }
                list.add(element)
            }
            return result
        }

        fun <T> List<T>.groupAllByThis(f: Int): Map<Int, List<T>> {
            val result = mutableMapOf<Int, MutableList<T>>()
            for (element in this) {
                val list = result.getOrPut(f) { mutableListOf() }
                list.add(element)
            }
            return result
        }

        personList.groupBy(Person::age) eq mapByAge
        personList.groupByAge(1, Person::age) eq mapByAge
        personList.groupByAge(1) { it.name.first() } eq
                mapOf('B' to listOf(bill), 'C' to listOf(chad), 'D' to listOf(devin, dustin))
        personList.groupByAge2 { it.age } eq mapByAge
        personList.groupAllByThis(20) eq mapAllByThis
    }

    data class Person(
        val name: String,
        val age: Int,
    )

    init {
        runAll(
            "Generic type examples",
            Section("Define generic in constructor", ::defineGenericConstructor),
            Section("Any holders", ::anyHolder),
            Section("Star projections", ::starProjection),
            Section("getOrPut() examples", ::getOrPutExamples),
        )
    }
}