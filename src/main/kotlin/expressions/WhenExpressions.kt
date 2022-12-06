package expressions

//the first successful match completes the execution of a when expression
class WhenExpressions {
    fun whenExpressionsBody(a: Int): String =
        when (a) {
            1 -> "One"
            2 -> {
                "Two"
            }
            else -> "Else"
        }

    fun whenBlockBody(a: Int): String {
        return when (a) {
            1, 2 -> "One or Two"
            3, 4 -> "Three or Four"
            else -> "Else"
        }
    }

    fun whenElseNotNeeded(a: Int): Unit {
        when (a) {
            in 1..2 -> "One or Two"
            in 3..4 -> return //cancels outer function (whenElseNotNeeded)
            in 5..6 -> {
            } //do nothing
        }
    }

    fun whenNoArguments(a: Double): String {
        return when {
            a < 0 -> "Less than zero"
            a > 0 -> "Greater than zero"
            else -> "Zero"
        }
    }

    //order doesn't matter in Sets
    fun whenSets(a: String, b: String): String =
        when (setOf(a, b)) {
            setOf("red", "blue") -> "purple"
            setOf("red", "yellow") -> "orange"
            else -> "unknown"
        }

    fun multipleBlocks(a: String?, b: String?) {
        when {
            a != null -> println("a is not null") //exists when true
            b != null -> println("b is not null")
        }
    }
}