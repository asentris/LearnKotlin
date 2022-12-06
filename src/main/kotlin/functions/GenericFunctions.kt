package functions

import atomicTest.eq

class GenericFunctions {
    //the angle brackets are written before the function name
    class GenericFunctionSyntax {
        fun <T> genericFunction(a: T): T = a

        fun testGenericFunction() {
            genericFunction("Hello") eq "Hello"
            genericFunction(1) eq 1
            val dog = genericFunction(Dog())
            dog.bark() eq "Ruff!"
        }
    }

    class GenericExtensionFunction {
        fun <T> List<T>.firstOrNull(): T? =
            when {
                isEmpty() -> null
                else -> first()
            }

        fun testFirstOrNull() {
            val intList = listOf(1, 2, 3)
            val stringList = listOf("a", "b", "c")
            val nullList = listOf<Int>()
            intList.firstOrNull() eq 1
            stringList.firstOrNull() eq "a"
            nullList.firstOrNull() eq null
        }
    }
    class Dog {
        fun bark() = "Ruff!"
    }
}