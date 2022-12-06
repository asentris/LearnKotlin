package functions

//extensions can only access public elements of the type being extended
//they provide syntax benefits, as the IDE will display them as functions that
//can be called when typing in the class

//these are also good for adding functionality without inheritance

class ExtensionFunctions {
    fun String.addHello() = "${this}Hello" //adds a member function to String
    fun String.addGoodbye() = addHello() + "Goodbye"
    fun String.wrapInTag(tagName: String): String = "<$tagName>$this</$tagName>"
}