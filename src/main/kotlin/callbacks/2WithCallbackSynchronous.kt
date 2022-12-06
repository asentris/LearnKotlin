package callbacks

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class WithCallbackSynchronous {

    init {
        val fred = Barista()
        val sam = Barista()

        fred.acceptOrder(BrewTime.LONG)
        sam.acceptOrder(BrewTime.SHORT)
    }

    private class Barista : CoffeeMaker.OnCoffeeBrewedListener {
        private val coffeeMaker = CoffeeMaker()

        fun acceptOrder(brewTime: BrewTime) {
            val coffee = coffeeMaker.brewCoffee(brewTime, this)
        }

        override fun onCoffeeBrewed(coffee: Coffee) {
            println("Finished brewing ${coffee.brewTime} brew time.")
        }
    }

    private data class Coffee(val brewTime: BrewTime)

    private class CoffeeMaker {
        fun brewCoffee(brewTime: BrewTime, callback: OnCoffeeBrewedListener) {
            delay(brewTime.length) // Simulates time to make coffee
            val madeCoffee = Coffee(brewTime)
            callback.onCoffeeBrewed(madeCoffee)
        }

        interface OnCoffeeBrewedListener {
            fun onCoffeeBrewed(coffee: Coffee)
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