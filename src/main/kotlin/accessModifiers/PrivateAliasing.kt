package accessModifiers

class Counter(var start:Int) {
    fun increment(){
        start+=1
    }

    override fun toString(): String =
        start.toString()
}
class CounterHolder(counter:Counter){
    private val ctr=counter //this private property value is still editable since it is a reference to an object
    override fun toString(): String {
        return "CounterHolder: "+ctr
    }
}
/*
fun main(){
    val c=Counter(11)
    val ch=CounterHolder(c)
    println(ch) //"CounterHolder: 11"
    c.increment()
    println(ch) //"CounterHolder: 12"
    val ch2=CounterHolder(Counter(9))
    println(ch2) //"CounterHolder: 9"
}
*/