package classTypes.inheritance

import Section
import atomicTest.eq
import atomicTest.neq
import runAll

//this is when a subclass becomes a superclass and all extra properties and methods are stripped
//(info also in generics)
class Upcasting {
    //all overridden properties or functions will still result in the subclass specific implementation
    private fun upcast() {
        class ToPet(private val pet: Pet) {
            fun getAnyConstr() = pet
        }

        val holder = ToPet(Dog())
        val fakeDog = holder.getAnyConstr()
        val realDog = Dog()
        fakeDog::class eq realDog::class //class remains the same
        fakeDog::class.isInstance(Dog()) eq realDog::class.isInstance(Dog()) //both are instances of Dog()
        fakeDog::hashCode neq realDog::hashCode //fakeDog becomes type Pet() and properties are stripped
        //fakeDog.bark() = won't compile since its Dog() properties are stripped
        realDog.bark()
    }

    private fun anyHolder() {
        //the Any type can be used for almost anything, but is limited since it removes the type references for objects:
        class AnyConstructor(private val anyConstr: Any) {
            fun getAnyConstr(): Any = anyConstr
        }

        val holder = AnyConstructor(Pet())
        val fakePet = holder.getAnyConstr()
        val realPet = Pet()
        fakePet::class eq realPet::class //class remains the same
        fakePet::class.isInstance(Pet()) eq realPet::class.isInstance(Pet()) //both are instances of Pet()
        fakePet::hashCode neq realPet::hashCode //fakePet becomes type Any() and properties are stripped
        //fakePet.eat() = won't compile since any is assigned as an Any, not a Pet and can't eat()
        realPet.eat()
    }

    //this is like upcasting, but upcasts all the way to Any()
    private fun starProjection() {
        //a star projection removes all type info (called "type erasure") for the object passed in
        val gary = Person("Gary", 30)
        val list: List<*> = listOf(gary)
        list[0] eq gary
        list[0]!!::class eq gary::class //class remains the same
        list[0]::hashCode neq gary::hashCode //list[0] becomes Any? and properties are stripped
    }

    data class Person(
        val name: String,
        val age: Int,
    )

    open class Pet {
        fun eat() = println("Chow")
    }

    class Dog : Pet() {
        fun bark() = println("Ruff!")
    }

    init {
        runAll(
            "Upcasting examples",
            Section("Upcast example", ::upcast),
            Section("Upcast as type Any()", ::anyHolder),
            Section("Star projections", ::starProjection),
        )
    }
}