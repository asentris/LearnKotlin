package other.operators

import Section
import atomicTest.eq
import runAll
import kotlin.reflect.KClass

class Reference { // ::

    private var firstClassProperty = 1

    private class AcceptJavaClass(val a: Class<*>)
    private class AcceptKotlinClass(a: KClass<*>)
    private class MyOuterClass {
        inner class InnerClass
    }

    private fun functionReference() {
        val list = listOf(1, 2, 3)
        fun isOdd(i: Int) = i % 2 != 0 //function context that is assumed in list.filer
        fun isOdd(s: String) = s == "x" || s == "X" //overloaded function ignored in list.filer
        val predicate: (Int) -> Boolean = ::isOdd //refers to isOdd(i: Int)

        list.filter(::isOdd) eq listOf(1, 3) //refers to isOdd(i: Int), which is assumed by the context
        list.filter(predicate) eq listOf(1, 3)
        1234.run(Int::toString) eq 1234.toString() //refers to extension function of Int

        fun String.isShort() = this.length < 4
        val noReceiver = String::isShort
        val withReceiver: (String) -> Boolean = String::isShort
        "1234".run(noReceiver) eq "1234".run(withReceiver)
    }

    private fun propertyReference() {
        ::firstClassProperty.set(2)
        ::firstClassProperty.get() eq 2
        ::firstClassProperty.name eq "firstClassProperty"
        val memberProperty = AcceptJavaClass::a
    }

    private fun classReference() {
        val javaClassRef = AcceptJavaClass(AcceptKotlinClass::class.java)
        val kotlinClassRef = AcceptKotlinClass(AcceptJavaClass::class)
        javaClassRef::class eq AcceptJavaClass::class
        MyOuterClass()::InnerClass //bound callable reference to inner class constructor
    }

    init {
        runAll(
            "Reference operator",
            Section("Function reference", ::functionReference),
            Section("Property reference", ::propertyReference),
            Section("Class reference", ::classReference),
        )
    }
}