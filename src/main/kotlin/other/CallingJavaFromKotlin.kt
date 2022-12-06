package other

import Section
import atomicTest.eq
import runAll

class CallingJavaFromKotlin {

    //backticks are used around method names when they are the same as Kotlin keywords
    private fun `is`() = 0

    private fun backtickEscaping() {
        CallingJavaFromKotlin().`is`() eq 0
    }

    private fun platformTypes() { //?????
        //null checks for platform types are unsafe, as they are in Java
        val list = ArrayList<String>() // non-null (constructor result)
        list.add("Item")
        val size = list.size // non-null (primitive int)
        val item = list[0] // platform type is inferred (ordinary Java object)
        item.substring(1) // allowed, may throw an exception if item == null
        val nonNull: String = item
        val nullable: String? = item

        /** Platform Type Notation
         * Platform types can't be explicitly mentioned in a program, so there's no syntax for them in the language.
         * However, they are displayed in error messages or in parameter info through their mnemonic notation:
         * T!                       means T or T?
         * (Mutable)Collection<T>!  means Java collection of T may be mutable or not, may be nullable or not
         * Array<(out) T>!          means Java array of T (or a subtype of T ), nullable or not
         **/
    }

    init {
        runAll(
            "Calling Java from Kotlin",
            Section("Escaping for Java identifiers that are keywords in Kotlin", ::backtickEscaping),
            Section("Null-safety and platform types", ::platformTypes),
        )
    }
}