package coroutineEx

import Section
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import runAll

//asynchronous data stream that sequentially emits values & completes normally or with an exception
//flow is cold - does calculation & emits result, everytime flow is collected, it will rerun the calculation & emit it
class FlowEx {

    private val ints: Flow<Int> = flow {
        for (i in 0..9) {
            delay(100)
            emit(i)
        }
    }

    private val chars: Flow<Char> = flow {
        for (i in 'A'..'J') {
            delay(100)
            emit(i)
        }
    }

    private val intsList = mutableListOf<Int>()

    private fun collectInts() {
        println("1 $intsList")
        CoroutineScope(Dispatchers.Default).launch {
            ints.collect { intsList.add(it) }
            println("2 $intsList")
        }.invokeOnCompletion {
            println("3 $intsList")
        }
        println("4 $intsList")
        runBlocking { delay(2000) }
    }

    private fun mapExample() {
        fun <T, R> Flow<T>.mapExtension(transform: suspend (value: T) -> R) = flow {
            collect { emit(transform(it)) }
        }

        CoroutineScope(Dispatchers.Default).launch {
            ints.mapExtension { println(it * it) }
        }
        runBlocking { delay(2000) }
    }

    private fun produceExample() {
        fun <T> Flow<T>.buffer(size: Int = 0): Flow<T> = flow {
            //calls specified suspend block with this scope; designed for parallel decomposition of work
            //when any child coroutine in this scope fails, this scope fails & all other children are cancelled
            //returns as soon as the given block & all its children coroutineEx are completed
            coroutineScope {
                //produce() combines launching a new coroutine & creating a channel
                val channel = produce(capacity = size) {
                    collect { send(it) }
                }
                channel.consumeEach { emit(it) }
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            ints.buffer().collect {
                delay(100)
                print(it)
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            chars.collect {
                delay(100)
                print(it)
            }
        }
        runBlocking { delay(1400) }
        println()
    }

    private fun flowOnExample() {
        fun flowContext(): Flow<Int> = flow {
            for (i in 1..3) {
                emit(i * i) //CPU intensive operation
            }
        }.flowOn(Dispatchers.Default) //changes context for the flow before it, but emits in collector context

        fun sample(int: Int): Int = int

        flowContext()
            .map { sample(it) } //in context declared by flowOn()
            .flowOn(Dispatchers.IO)
            .map { sample(it) } // in context declared by collector

        CoroutineScope(Dispatchers.IO).launch {
            flowContext().collect { println(it) }
        }
        runBlocking { delay(1000) }
        println()
    }

    //performing an action after completion of flow collection
    private fun <T> Flow<T>.onCompleted(action: () -> Unit) = flow {
        collect { value -> emit(value) } // reemit all values from the original flow
        action() // this code runs only after the normal completion
    }

    private fun errorHandling() {
        fun boilerPlateExample() {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    ints.collect { error("Failed - boilerPlateExample()") }
                } catch (e: Throwable) {
                    println(e)
                }
            }
            runBlocking { delay(200) }
        }
        boilerPlateExample()

        //the exception is caught in handleErrors1 and completes normally, while it should be propagated to the collector
        fun badExample() {
            fun <T> Flow<T>.handleErrors1(): Flow<T> = flow {
                try {
                    collect { value -> emit(value) }
                } catch (e: Throwable) {
                    println(e)
                }
            }

            CoroutineScope(Dispatchers.Default).launch {
                ints
                    .handleErrors1()
                    .collect { error("Failed - badExample()") }
            }
            runBlocking { delay(200) }
        }
        badExample()

        //does not catch errors that happen in .collect { error("Failed") } due to exception transparency
        fun badCatchExample() {
            fun <T> Flow<T>.handleErrors2(): Flow<T> = catch { e -> println(e) }

            CoroutineScope(Dispatchers.Default).launch {
                ints
                    .handleErrors2()
                    .collect { error("Failed - batCatchExample()") }
            }
            runBlocking { delay(200) }
        }
        badCatchExample()

        fun acceptableCatchExample() {
            fun <T> Flow<T>.handleErrors2(): Flow<T> = catch { e -> println(e) }

            CoroutineScope(Dispatchers.Default).launch {
                ints
                    .onEach { error("Failed - acceptableCatchExample()") }
                    .handleErrors2()
                    .collect()
            }
            runBlocking { delay(200) }
        }
        acceptableCatchExample()

        fun simplifiedCatchExample() {
            fun <T> Flow<T>.handleErrors2(): Flow<T> = catch { e -> println(e) }

            ints
                .onEach { error("Failed - simplifiedCatchExample()") }
                .handleErrors2()
                .launchIn(CoroutineScope(Dispatchers.Default))
            runBlocking { delay(200) }
        }
        simplifiedCatchExample()
    }

    init {
        runAll(
            "Flow examples",
            Section("Collecting flow", ::collectInts),
            Section("Extension function", ::mapExample),
            Section("produce() example", ::produceExample),
            Section("flowOn() example", ::flowOnExample),
            Section("Error handling examples", ::errorHandling),
        )
    }
}