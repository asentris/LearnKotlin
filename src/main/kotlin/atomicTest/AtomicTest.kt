package atomicTest

import kotlin.math.abs
import kotlin.reflect.KClass

const val ERROR = "[ATOMIC TEST ERROR]:"
const val PASS = "[ATOMIC TEST PASS]: "

private fun <L, R> test(
    actual: L,
    expected: R,
    checkEquals: Boolean = true,
    predicate: () -> Boolean
) {
    if (predicate()) {
        println(PASS + actual + (if (!checkEquals) " != $expected" else ""))
    } else {
        print(ERROR)
        println("$actual " + (if (checkEquals) "!=" else "==") + " $expected")
    }
}

//compares the string representation of this object with the string rval
infix fun Any.eq(rval: String) {
    test(this, rval) {
        toString().trim() == rval.trimIndent()
    }
}

//verifies this object is equal to rval
infix fun <T> T.eq(rval: T) {
    test(this, rval) {
        this == rval
    }
}

//verifies this object is != rval
infix fun <T> T.neq(rval: T) {
    test(this, rval, checkEquals = false) {
        this != rval
    }
}

//verifies that a Double number is equal to rval within a positive delta
infix fun Double.eq(rval: Double) {
    test(this, rval) {
        abs(this - rval) < 0.0000001
    }
}

//holds captured exception information
class CapturedException(
    private val exceptionClass: KClass<*>?,
    private val actualMessage: String
) {
    private val fullMessage: String
        get() {
            val className = exceptionClass?.simpleName ?: ""
            return className + actualMessage
        }

    infix fun eq(message: String) {
        fullMessage eq message
    }

    infix fun contains(parts: List<String>) {
        if (parts.any { it !in fullMessage }) {
            print(ERROR)
            println("Actual message: $fullMessage")
            println("Expected parts: $parts")
        }
    }

    override fun toString(): String {
        return fullMessage
    }
}

//captures an exception and produces information about it. Usage:
//capture{
//  Code that fails
//} eq "FailureException: message"
fun capture(f: () -> Unit): CapturedException =
    try {
        f()
        CapturedException(null, "$ERROR Expected an exception")
    } catch (e: Throwable) {
        CapturedException(e::class, (e.message?.let { ": $it" } ?: ""))
    }

//Accumulates output when called as in: trace("info"), trace(object).
//Later compares accumulated to expected: trace eq "expected output"
object trace {
    private val trc = mutableListOf<String>()
    operator fun invoke(obj: Any?) {
        trc += obj.toString()
    }

    //compares trc contents to a multiline String by ignoring white space
    infix fun eq(multiline: String) {
        val trace = trc.joinToString("\n")
        val expected = multiline.trimIndent().replace("\n", " ")
        test(trace, multiline) {
            trace.replace("\n", " ") == expected
        }
        trc.clear()
    }
}