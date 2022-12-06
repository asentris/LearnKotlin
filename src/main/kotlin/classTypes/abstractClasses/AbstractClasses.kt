package classTypes.abstractClasses

//abstract classes can have functions without a definition or uninitialized properties
//abstract classes can also contain "state", while interfaces cannot
class AbstractClasses {
    abstract class AbstractClass(a: Int){
        abstract val x: Int
        abstract val z: Int
        abstract fun a(): Int
        abstract fun b() //Unit is assumed as return type
        fun c() = 1 //this is still possible in an interface

        val y = 10 //this is not possible in an interface
    }
}