package constructors.complex

class SecondaryConstructors {

    //"all", 'a', 'b', property1, property2, & init section are all together called the primary constructor
    class WithInit(a: String, b: String) {
        val all = 0
        private val property1: String
        private val property2: String

        init {
            property1 = a
            property2 = b
            println("1")
        }

        //a constructor must call the primary constructor or a secondary constructor with "this"
        //the call with "this" completes before the constructor's body is started
        constructor(e: Int, f: Int) : this(e.toString(), f.toString()) {
            println("2") //secondary constructor bodies are optional
        }

        constructor(k: Double) : this(k.toString(), k.toString()) {
            println("3")
        }
    }

    //inits are not needed to use constructors
    class WithoutInit(val a: String) {
        private var all = ""

        constructor(c: Char, d: Char) : this(c.toString() + d.toString()) {
            all += d
            println("2")
        }

        constructor(k: Long) : this(k.toString()) {
            println("3")
        }
    }
}