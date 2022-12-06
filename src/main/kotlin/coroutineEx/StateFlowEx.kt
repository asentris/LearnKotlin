package coroutineEx

import Section
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import runAll

//contains only one value, unlike shared flow
//supports nullability
//ignores new state when value is the same
class StateFlowEx {
    private val mutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    private val example1 = MutableStateFlow<Int>(-1)
    private fun sharedVsStateFlow() { //results in -1 & 10 being printed
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
            "StateFlow examples",
            Section("Shared vs State Flow example", ::sharedVsStateFlow),
        )
    }
}