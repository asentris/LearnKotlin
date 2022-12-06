package other.operators

import Section
import atomicTest.eq
import runAll

class IndexedAccess {

    private val a = listOf('a', 'b', 'c', 'd')

    private fun examples() {
        a[1] eq a.get(1)

        //map[1, 2] eq a.get(1, 2)
        //a[i,..., l] eq a.get(i,..., l)
        //a[i] = b eq a.set(i, b)
        //a[i, j] = b eq a.set(i, j, b)
        //a[i,..., l] = b eq a.set(i,..., l, b)
    }

    init {
        runAll(
            "Indexed access operators",
            Section("Examples", ::examples),
        )
    }
}