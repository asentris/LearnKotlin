package expressions

class IfExpressions {

    val ifExpressionBody = if (true) 1 else -1

    fun ifStatement1(x: Int) {
        if (x >= 2)
            println("Greater than or equal to 2")
        if (x >= 1)
            println("Greater than or equal to 1")
        else if (x >= 0)
            println("Greater than or equal to 0 but less than 1")
        else
            println("Any other instance")
    }

    fun ifStatement2(x: Int) {
        val a: Boolean = x >= 0 //assigns true when x>=0, otherwise it assigns false
        if (a)
            println("True")
        else
            println("False")
    }

    fun ifExpression(exp: Boolean): String {
        if (exp)
            return "True"
        return "False"
    }

    fun activateIfExpression(x: Int) {
        val a = ifExpression(x >= 0)
        println(a)
    }

    fun ifExpressionV2(exp: Boolean): String =
        if (exp)
            "True"
        else
            "False"

    fun activateIfExpressionV2(x: Int) {
        val a = ifExpressionV2(x >= 0)
        println(a)
    }
}