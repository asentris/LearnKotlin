package functions.scopeFunctions

import atomicTest.eq

//receiver is "it" by default, or any declared name
//return object is "this"
//same as "apply", but used when not wanting to shadow "this"
class Also {

    fun runAll(){
        MyScopeClass().also {
            it::class eq MyScopeClass()::class
            this::class eq Also()::class
        }
    }
}