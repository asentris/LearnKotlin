package properties

//getters are created by defining get() immediately after the property definition
//setters are created for mutable properties by defining set() immediately after the property definition

class PropertyAccessors {
    var i: Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }
}

//this makes the setter private so that it can only be incremented by one using the fun inc()
//it can still be retrieved from outside the class
class Counter {
    var incrementByOne = 0
        private set

    fun inc() = incrementByOne++
}

//retrieveState has no underlying data stored in it and instead gets its data from state when it is accessed
//retrieveState is essentially a function and can be written as retrieveState() instead of as a property
class noState {
    private val state = 0
    val retrieveState: Int
        get() = state

    fun retrieveState() = state
}