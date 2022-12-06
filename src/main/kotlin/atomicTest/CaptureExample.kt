package atomicTest

fun captureExample(){
    capture { "1$".toInt() } eq """NumberFormatException: For input string: "1$""""
}