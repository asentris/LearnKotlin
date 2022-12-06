package classTypes.interfaces

class SingleAbstractMethod {
    //"fun interface" ensures there is only a single member function
    fun interface ZeroArg {
        fun f(): Int
    }

    fun interface OneArg {
        fun f(a: Int): Int
    }

    fun interface TwoArg {
        fun f(a: Int, b: Int): Int
    }

    fun verboseImplementations() {
        //a class must be defined to create an object:

        class Zero1 : ZeroArg {
            override fun f() = 20
        }

        val zero2 = Zero1()

        class One1 : OneArg {
            override fun f(a: Int) = a + 20
        }

        val one2 = One1()

        class Two1 : TwoArg {
            override fun f(a: Int, b: Int) = a + b + 20
        }

        val two2 = Two1()
    }

    fun samImplementations() {
        //with SAM conversions (below), no class is declared to create an object instance
        val zero3 = ZeroArg { 1 }
        val one3 = OneArg { it + 1 }
        val two3 = TwoArg { x, y -> x + y + 1 }
    }

    fun passLambda() {
        fun takeZero(zeroArg: ZeroArg) {}
        takeZero { 1 }
    }
}