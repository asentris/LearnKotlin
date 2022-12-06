package constructors

import atomicTest.eq

class DefaultArguments(
    red: Int = 0,
    green: Int = 0,
    blue: Int = 0, //trailing commas make diffs easier to read
) {
    val defaultRed = red
    val defaultGreen = green
    val defaultBlue = blue
}

class DefaultObjectArgument(val a: DefaultArguments = DefaultArguments()) //automatically creates object instance

fun verifyDefaultArguments() {
    DefaultArguments().defaultRed eq 0
    DefaultArguments(green = 10).defaultGreen eq 10
    DefaultArguments(10, 10, 10).defaultBlue eq 10
}