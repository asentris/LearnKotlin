package accessModifiers

//private classes, top-level functions, and top-level properties are accessible only within that file

private var topLevelProperty=0 //top-level property

private class Private(val name:String) { //top-level class

    //only members of this class can access private properties, functions, or inner classes within this class

    private var classProperty=0

    private fun classFunction(){}
}
private fun topLevelFunction(myParameter:Private){}