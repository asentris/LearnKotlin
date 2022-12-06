package expressions

class RepeatExpressions {
    fun repeatBlock(s: String) {
        repeat(3) {
            println(s)
        }
    }

    val a: Unit = repeat(3) { println("a") }
}