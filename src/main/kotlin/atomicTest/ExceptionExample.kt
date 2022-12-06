package atomicTest

fun averageIncome(income: Int, months: Int) =
    if (months <= 0)
        throw IllegalArgumentException(
            "Months can't be zero or less")
    else
        income / months

fun repeatChar(ch: Char, n: Int): String {
    if (n < 0)
        throw IllegalArgumentException("Count 'n' must be non-negative, but was $n.")
    var s = ""
    repeat(n) {
        s += ch
    }
    return s
}