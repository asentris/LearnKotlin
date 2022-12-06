package classTypes.inheritance

import atomicTest.eq
import atomicTest.neq

class Polymorphism {
    //this upcasts to the parent class, but when an overridden function is called on the object instance,
    //the child implementation is used
    fun polymorph(){
        class ToPet(private val pet: Pet) {
            fun getAnyConstr() = pet
        }

        val holder = ToPet(Dog())
        val fakeDog = holder.getAnyConstr()
        val realDog = Dog()
        fakeDog::class eq realDog::class //class remains the same
        fakeDog::class.isInstance(Dog()) eq realDog::class.isInstance(Dog()) //both are instances of Dog()
        fakeDog::hashCode neq realDog::hashCode //fakeDog becomes type Pet() and properties are stripped
        fakeDog.sound() eq "Ruff!" //still "Ruff!" even though properties are stripped
        realDog.sound() eq "Ruff!"
    }
    open class Pet {
        open fun sound() = "..."
    }
    class Dog : Pet() {
        override fun sound() = "Ruff!"
    }
}