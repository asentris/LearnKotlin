package coroutineEx

import Section
import kotlinx.coroutines.*
import runAll

class ExceptionHandler {

    @OptIn(DelicateCoroutinesApi::class)
    private fun uncaughtExceptionInLaunchPropagates() {
        runBlocking {
            val job = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
                throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
            }
            job.join()
            println("Joined failed job")
            val deferred = GlobalScope.async { // root coroutine with async
                println("Throwing exception from async")
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            try {
                deferred.await()
                println("Unreached")
            } catch (e: ArithmeticException) {
                delay(1000)
                println("Caught ArithmeticException")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun caughtException() {
        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }
            val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
                throw AssertionError()
            }
            val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
                throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
            }
            joinAll(job, deferred)
        }
    }

    private fun cancelledCoroutine() {
        runBlocking {
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("Child is cancelled")
                    }
                }
                yield()
                println("Cancelling child")
                child.cancel() //terminates, but does not cancel the parent (finally block still completes)
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun waitingForChildrenToFinish() {
        runBlocking {
            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }
            val job = GlobalScope.launch(handler) {
                launch { // the first child
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        withContext(NonCancellable) {
                            println("Children are cancelled, but exception is not handled until all children terminate")
                            delay(100)
                            println("The first child finished its non cancellable block")
                        }
                    }
                }
                launch { // the second child
                    delay(10)
                    println("Second child throws an exception")
                    throw ArithmeticException()
                }
            }
            job.join()
        }
    }

    init {
        runAll(
            "Exception handling",
            Section("Uncaught exception in launch propagates", ::uncaughtExceptionInLaunchPropagates),
            Section("Caught exception", ::caughtException),
            Section("Cancelled coroutine", ::cancelledCoroutine),
            Section(
                "The original exception is handled by the parent only after all its children terminate",
                ::waitingForChildrenToFinish
            ),
        )
    }
}