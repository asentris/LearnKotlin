package coroutineEx

import Section
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import runAll

//shared flow is hot - emits values to all consumers that collect from it and stores a list of elements
//stored list is called replayCache
//does NOT ignore new state when value is the same
class SharedFlowEx {
    private val mutableSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(
        replay = 3, //# of elements stored in shared flow and replayed to subscriber
        extraBufferCapacity = 2, //# values buffered in addition to replay
        onBufferOverflow = BufferOverflow.SUSPEND, //what to do when the flow is overwhelmed
    )

    private val example1 = MutableSharedFlow<Int>()
    private fun sharedVsStateFlow() { //results in 0 through 10 being printed
        CoroutineScope(Dispatchers.Default).launch {
            example1.collect { println(it) }
        }
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            (0..10).forEach { example1.emit(it) }
        }
        runBlocking { delay(2000) }
    }

    init {
        runAll(
            "SharedFlow examples",
            Section("Shared vs State Flow example", ::sharedVsStateFlow),
        )
    }
}