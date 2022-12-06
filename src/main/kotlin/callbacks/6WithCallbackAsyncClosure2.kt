package callbacks

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class WithCallbackAsyncClosure2 {

    init {
        val fred = Barista()
        val sam = Barista()

        fred.acceptOrder(BrewTime.LONG)
        sam.acceptOrder(BrewTime.SHORT)
    }

    private class Barista {
        private val coffeeMaker = CoffeeMaker()

        private val onFinished: (Coffee) -> Unit = {
            println("Finished brewing ${it.brewTime} brew time.")
        }

        fun acceptOrder(brewTime: BrewTime) {
            coffeeMaker.brewCoffee(brewTime, onFinished)
        }
    }

    private data class Coffee(val brewTime: BrewTime)

    private class CoffeeMaker {
        fun brewCoffee(brewTime: BrewTime, onBrewed: Coffee.() -> Unit) {
            delay(brewTime.length) { // Simulates time to make coffee (ASYNC)
                val madeCoffee = Coffee(brewTime)
                onBrewed(madeCoffee)
            }
        }

        private fun delay(time: Long) {
            Thread.sleep(time)
        }

        private fun delay(time: Long, complete: () -> Unit) {
            val completableFuture = CompletableFuture<String>()
            Executors.newCachedThreadPool().submit<Any?> {
                Thread.sleep(time)
                completableFuture.complete("")
                null
            }
            completableFuture.thenAccept { complete() }
        }
    }

    private enum class BrewTime(val length: Long) {
        SHORT(250L),
        LONG(1000L),
    }
}