package functions

class Functions {
    fun blockBody1(x:Int):String{ //return type is String
        return "$x"
    }
    fun blockBody2(x:Int){ //return type is blank, so Unit is inferred and returned
        println("Hello")
    }
    fun blockBody3(){ //parameters and return type are blank, return type is still Unit
        println("Hello")
    }

    fun expressionBody1(x:Int):String="$x" //return type is String and is the result of the expression
    fun expressionBody2(x:Int)=x*3 //return type is inferred and is the result of the expression (Int)
}