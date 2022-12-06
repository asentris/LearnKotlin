package loops

import atomicTest.eq

//break and continue can only be used within For, While, & Do While loops
//break ends the entire loop
//continue jumps to the beginning of a loop at the next iteration
class BreakContinue {
    fun example() {
        for (i in 1..5) {
            when{
                i==2 -> continue
                i==4 -> break
            }
            println(i)
        }
    }
    //labels allow break & continue to work beyond the local loop to enclosing loops
    //they are written with the chosen name combined with the symbol '@'
    //break@label ends the loop that is labeled and its enclosed loops
    //continue@label jumps to the beginning of the labeled loop at its next iteration
    fun labelExample(){
        val list = mutableListOf<String>()
        outer@ for(i in 'a'..'c') {
            for (j in 1..3) {
                if(j == 2) continue@outer
                if("$i$j" == "b1") break@outer
                list.add("$i$j")
            }
        }
        list eq listOf("a1")
    }
}