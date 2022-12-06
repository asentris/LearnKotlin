package classTypes.interfaces

//interfaces cannot contain "state", while abstract classes can
//in other words, properties cannot be initialized within an interface
//all functions and properties within an interface are abstract by default, so it is redundant to use
//interfaces allow multiple interface inheritance, while prohibiting multiple state inheritance
class Interfaces {
    interface Screen {
        fun collision() = 1
    }

    interface Mouse {
        fun collision() = 1
    }

    interface Computer: Screen, Mouse { //interface inheriting more interfaces
        //"symbol" and prompt() are declared, but not implemented
        val symbol: Char
        fun prompt(): String

        override fun collision() = super<Screen>.collision() //must override collision() since multiple
        //super types contain it; specific type is specified within <>

        //propertyAccessor does not need to be overridden since it has a property accessor
        val propertyAccessor: Int //since interfaces can contain function implementations, it can also
            //contain a custom property accessor if the corresponding property does not change state:
            get() = 1

        //sample() does not need to be overridden since it is implemented
        fun sample() = 1 //interfaces can still contain functions with implementations
    }

    //a class that implements the interface must provide bodies for the declared functions and override properties
    //thus making the functions concrete
    class Desktop : Computer, Screen { //class Desktop implements interfaces Computer and Screen
        override val symbol = 'D'
        override fun prompt() = "Hello"
    }

    class DeepThought : Computer {
        override val symbol get() = '?'
        override fun prompt() = "Thinking..."
    }

    class Quantum(override val symbol: Char) : Computer {
        override fun prompt() = "Probably..."
    }
}