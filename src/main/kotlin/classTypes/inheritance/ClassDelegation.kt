package classTypes.inheritance

class ClassDelegation {
    interface Vehicle {
        fun drive(distance: Int): String
        fun refill(quantity: Int): String
    }

    class Car : Vehicle {
        override fun drive(distance: Int) = "drove: $distance"
        override fun refill(quantity: Int) = "refilled: $quantity"
    }

    //to modify or expand functions in CarNoDI without inheriting CarNoDI, use class delegation:
    class SpecialtyCar : Vehicle{
        private val car = Car()
        override fun drive(distance: Int) = car.drive(distance)
        override fun refill(quantity: Int) = car.refill(quantity + 100)
    }

    //use the "by" keyword to automate the class delegation implementation
    //below is essentially the same result as SpecialtyCar
    //"class SpecialtyCarAlternate implements interface Vehicle by using the "car" member object"
    //this can include multiple "this by that" cases, simulating multiple class inheritance
    //a created object from this can then be upcast to either of its delegated types
    class SpecialtyCarAlternate(private val car: Car = Car()): Vehicle by car{
        override fun refill(quantity: Int) = car.refill(quantity + 100)
    }
}