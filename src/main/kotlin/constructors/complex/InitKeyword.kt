package constructors.complex

private var myCounter = 0

class InitKeyword {

    class ConstructorBody(a: Int){
        private val content: String

        //init sections are executed during object creation, and are the first part of the body executed
        init {
            val temp = 0 //temp is not accessible outside init
            myCounter += 1

            //constructor parameters ('a') are accessible inside init even when they aren't marked with "val" or "var"
            content = "$a"
        }

        override fun toString(): String {
            return content
        }
        init {
            //there can be more than one init in a class
        }
    }
}