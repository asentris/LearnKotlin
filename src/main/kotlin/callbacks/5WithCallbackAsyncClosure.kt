package callbacks

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class WithCallbackAsyncClosure {

    init {
        val fred = Barista()
        val sam = Barista()

        fred.acceptOrder(BrewTime.LONG)
        sam.acceptOrder(BrewTime.SHORT)
    }

    private class Barista {
        private val coffeeMaker = CoffeeMaker()

        fun acceptOrder(brewTime: BrewTime) {
            coffeeMaker.brewCoffee(brewTime) {
                println("Finished brewing ${this.brewTime} brew time.")
            }
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