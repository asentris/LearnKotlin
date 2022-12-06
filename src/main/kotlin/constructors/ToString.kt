package constructors

class ToString(val name:String) {
    //the default toString() function in every object creates a reference to the location of the object
    //when an object is used where a String is expected, the toString() function is called on that object

    override fun toString(): String {
        return name //this assigns the constructor argument as the result of toString()
    }
}