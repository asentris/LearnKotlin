package constructors

//parameter list must be unique, return types don't matter
class Overloading {
    fun overloaded() = 0
    fun overloaded(a: Int) = a + 2
}

//the member function of a class is preferred over an extension function with the same parameter list
//the extension function in this case will never be called
class MemberFunctionPreferred {
    fun foo() = 0
}

fun MemberFunctionPreferred.foo() = 1 //not callable
fun MemberFunctionPreferred.foo(a: Int) = 2 //callable

//the overloaded function closest to the one called will be chosen
//the default argument value in this case will never be used
class NoArgumentChosen {
    fun bar(a: Int = 0) = a + 1 //a=0 unusable
    fun bar() = 10
}