package constructors

class NamedArguments(red: Int, green: Int, blue: Int) {
}

fun specifyCertainNamedArguments(){
    NamedArguments(
        red = 0,
        1,
        blue = 2,
    )
}
fun changeNamedArgumentsOrder(){
    NamedArguments(
        green = 1,
        blue = 2,
        red = 5
    )
}