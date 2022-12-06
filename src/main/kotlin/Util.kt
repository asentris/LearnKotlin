class Section(
    val functionHeader: String,
    val function: () -> Unit,
    val function2: (Any?) -> Unit = {},
    val functionArg: Any? = null,
){
    constructor(
        functionHeader: String,
        function2: (Any?) -> Unit,
        functionArg: Any?,
    ): this(functionHeader, function = {}, function2, functionArg)
}

fun Any.runAll(header: String, vararg section: Section) {
    println(header.toUpperCase())
    println("*****************************************")
    for (i in section) {
        println("\n${i.functionHeader}:")
        i.function()
        i.function2(i.functionArg)
        println("- - - - - - - - - - - - - - - - - - - - -")
    }
}