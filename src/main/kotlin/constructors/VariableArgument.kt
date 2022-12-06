package constructors

class VariableArgument(name:String, vararg siblings:String) {
    //by using vararg keyword, an unlimited number of this parameter type can be added
    //this parameter becomes an Array of the type indicated
    //this means Arrays can be passed into a vararg parameter type
}

val mySiblingsArray = arrayOf("Edd", "Eddie")
fun passInSpreadOperator(name2: String, vararg siblings2:String){
    val createObject = VariableArgument("Ed", *mySiblingsArray)
}