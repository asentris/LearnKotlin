package classTypes.inheritance

import Section
import atomicTest.eq
import runAll

//smart casting doesn't work with open classes, since properties can be overridden
class Downcasting {
    private interface Base

    private class Child : Base {
        val mouth = "mouth"
        fun talk() = println("Hello")
    }

    private class StepChild : Base {
        fun speak() = println("Goodbye")
    }

    //smart casting is automatic downcasts in Kotlin using "is" keyword
    private fun smartCast() {
        val child: Base = Child() //upcast to Base
        //child.talk //this cannot compile because child is upcast as Base

        if (child is Child)
            child.talk() //talk() is within the scope of Child(), which "is" is defining and passed

        fun test(b: Base): Unit =
            when (b) {
                is Child -> b.talk()
                is StepChild -> b.speak()
                else -> println("Neither")
            }

        //smart casting to mutable (var) properties is not allowed due to concurrency and the possibility
        //that the object will change classes between the test and functions called on the downcast
        class testClass(var b: Base) {
            fun testVar() =
                when (val new = b) { //copying "b" to an immutable object
                    is Child -> new.talk()
                    is StepChild -> new.speak()
                    else -> println("Neither")
                }
        }
    }

    private fun asKeyword() {
        //using "as" is an unsafe cast and throws an exception if it fails
        fun childUnsafe(b: Base) = b as Child
        fun childUnsafe2(b: Base) {
            b as Child
            b.talk() //the as cast applies throughout the rest of the scope
            val string = (b as Child).mouth
            string eq "mouth"
        }
        childUnsafe2(Child())

        //using "as?" is a safe cast and returns null if it fails
        fun childSafe(b: Base) = b as? Child ?: Child() //use elvis operator to return Child rather than null
    }

    //the "is" keyword can be used to search through lists, which can then be downcast
    private fun isKeyword() {
        val list: List<Base> = listOf(Child(), StepChild(), Child())
        val firstChild = list.find { it is Child } as Child? //find returns a Base, "as" casts it as a nullable Child
        firstChild?.talk() //since firstChild is nullable, the safe call operator is used

        val firstChild2 = list.filterIsInstance<Child>().first()
        firstChild eq firstChild2

        val firstChild3 = list.filterIsInstance(Child::class.java).first()
        firstChild eq firstChild3
    }

    init {
        runAll(
            "Downcasting examples",
            Section("Smart casting", ::smartCast),
            Section("""Using the "as" keyword""", ::asKeyword),
            Section("""Using the "is" keyword""", ::isKeyword),
        )
    }
}