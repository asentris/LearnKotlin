package classTypes.inheritance

import atomicTest.eq

class Inheritance {
    //base class must be open; a non-open class doesn't allow inheritance: it is closed by default
    //this is opposite of Java, which must be declared "final" to be closed and is "open" by default
    //"final" modifier can be used in Kotlin, but is redundant
    open class Base(a: Int) {
        protected var number = 0 //"protected" properties can only be accessed from inherited classes
        var publicGetNumber = number //assigns actual value
            private set

        open fun add() { //functions must be open to be overridden
            number += 2
            println("ADD")
        }

        fun getNumberOut() = number //used to get "protected" property from outside inherited classes

        constructor(): this(0){
            //this can be called instead of the primary constructor
        }
    }

    //Derived inherits from Base; parent classes are constructed first
    //this derivative inherits from Base using a secondary, no argument constructor
    class Derived : Base() {
        var newNumber = number
        override fun add() {
            super.add()
            number += 3 //modifies the parent's property
            super.number += 3  //also modifies parent's property with "super"
        }

        fun addToNewNumber() {
            newNumber += 5
        }
    }

    //in the case there is only a primary constructor with a parameter, a value must be passed to Base
    //in this case, "number" is passed to Base
    class PassParameter(number: Int) : Base(number){
    }

    class CallSuperConstructor : Base{
        constructor(x: Int): super(x) //"x" is passed to the 1 parameter constructor for Base using "super"
        constructor(): this(1) //default is 1, which is passed to "x"
    }

    private fun Base.info() = publicGetNumber

    fun runAll() {
        val a = Derived()
        Derived().info() eq 0 //info() is inherited from Base; Derived "IS A" Base
        a.newNumber eq 0
        a.add()
        a.publicGetNumber eq 0
        a.getNumberOut() eq 8
    }
}