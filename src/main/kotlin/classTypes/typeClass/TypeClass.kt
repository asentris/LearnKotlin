package classTypes.typeClass

import kotlin.reflect.KClass

class TypeClass {
    fun createClass(){
        val a: KClass<Test> = Test::class
        val b: Class<Test> = Test::class.java
    }
    class Test
}