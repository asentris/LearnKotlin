package loops

import atomicTest.eq

class Labels {
    //labels can be used with lambdas to return to the start of the lambda
    fun lambdas() {
        val list = listOf(1, 2, 3)
        val value = 3
        var result = ""

        //specific label
        list.forEach lambdaStart@{
            result += "$it"
            if (it == value) return@lambdaStart
        }
        result eq "123"

        //function that called lambda
        list.forEach {
            result += "$it"
            if (it == value) return@forEach
        }
        result eq "123123"

        //exit fun lambdas()
        list.forEach {
            result += "$it"
            if (it == value) return
        }
        result eq "123123123" //not reached
    }

}