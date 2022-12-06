package loops

class ForLoops {
    fun forLoops(){
        for(i in 1..10){
            println(i) //1 2 3 4 5 6 7 8 9 10
        }
        for(i in -1..-2) println(i)
    }
    fun intProgression(i:IntProgression){
        for(a in i) //goes through IntProgression value
            println(a)
    }
    fun charProgression(){
        for(i in 'a'..'z') //goes through alphabet
            println(i) //a b c d e ...etc
    }
    fun stringProgression(){
        val myString="Hello Everyone!"
        for(i in 0..myString.lastIndex)
            print(myString[i]) //Hello Everyone!
    }
    fun stringASCII(){
        val mystery="Ifmmp!Fwfszpof\""
        for(i in 0..mystery.lastIndex)
            print(mystery[i]-1)
    }
    fun hasCharInString(s:String, c:Char):Boolean{ //Char can also be String, with i being a range
        for(i in s){
            if(i==c) return true
        }
        return false
    }
}