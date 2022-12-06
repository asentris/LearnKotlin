package functions

import Section
import atomicTest.eq
import runAll

class Recursion {
    //these are memory expensive; when calling a function, the info about the function and its
    //arguments are stored in a call tack

    private fun recursion() {
        fun factorial(n: Long): Long {
            if (n <= 1) return 1
            //else if (n == 2L) throw IllegalArgumentException() //uncomment to see long stack trace
            return n * factorial(n - 1)
        }

        //StackOverflowErrors occur when the stack becomes too large and exhausts available memory
        //this results in infinite recursion, where the chain of recursive calls doesn't stop
        fun sumDown(n: Long): Long {
            if (n == 0L) return 0
            return n + sumDown(n - 1)
        }

        //StackOverflowError 2:
        fun infiniteRecursion(i: Int): Int = infiniteRecursion(i + 1)

        factorial(6) eq 720
        //sumDown(100_000) eq 5000050000 //uncommenting will cause StackOverflowError
        //infiniteRecursion(1) //uncommenting will cause StackOverflowError
    }

    //tail recursion reduces the size of the stack call by making the function use only one stack
    //to work properly, recursion must be the final operation with no operations on the result
    //of the recursive call before it is returned (example: return n + sum() would not work)
    private fun tailRecursion() {
        //this first example shows one function as private, while the other is used to call it
        /*private*/ tailrec fun factorialPrivate(n: Long, sum: Long): Long =
            if (n <= 1) sum
            //else if (n == 2L) throw IllegalArgumentException() //uncomment to see short stack trace
            else factorialPrivate(n - 1, sum * n)

        fun factorial(n: Long) = factorialPrivate(n, 1)

        //this second example shows the private function used as an inner function (thus essentially private)
        fun sumDown(n: Long): Long {
            tailrec fun sumDown(n: Long, sum: Long): Long =
                if (n == 0L) sum
                else sumDown(n - 1, sum + n)
            return sumDown(n, 0)
        }

        fun fibonacci(n: Int): Long {
            tailrec fun fibonacci(n: Int, current: Long, next: Long): Long {
                if (n == 0) return current
                return fibonacci(n - 1, next, current + next)
            }
            return fibonacci(n, 0L, 1L)
        }

        tailrec fun infiniteRecursion(i: Int): Int = infiniteRecursion(i + 1)

        factorial(6) eq 720
        sumDown(100_000) eq 5000050000
        fibonacci(20) eq 6765
        //infiniteRecursion(1) //uncommenting will cause the program to hang (since it is infinite)
    }

    //Iterative (non-recursion) solutions
    private fun iterative() {
        fun factorial(n: Long): Long {
            var x = 1L
            for (i in 1..n) {
                x *= i
            }
            return x
        }

        fun sumDown(n: Long): Long {
            var x = 0L
            for (i in n downTo 1) {
                x += i
            }
            return x
        }

        fun fibonacci(n: Int): Long {
            var current = 0L
            var next = 1L
            repeat(n) {
                val x = current
                current = next
                next += x
            }
            return current
        }

        fun sumOfFactorials(n: Long): Long {
            var x = 0L
            for (i in 1..n) {
                var y = 1L
                for (i2 in 1..i) {
                    y *= i2
                }
                x += y
            }
            return x
        }

        factorial(6) eq 720
        sumDown(100_000) eq 5000050000
        fibonacci(20) eq 6765
        val x = factorial(3) + factorial(2) + factorial(1)
        x eq sumOfFactorials(3)
    }

    init {
        runAll(
            "Recursion vs iterative examples",
            Section("Recursion examples", ::recursion),
            Section("Tail recursion examples", ::tailRecursion),
            Section("Iterative (non-recursion) examples", ::iterative),
        )
    }
}