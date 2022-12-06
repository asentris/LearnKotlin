package other.operators

import atomicTest.eq

class Mathematical {

    private val ten = 10

    fun runAll() {
        ten + 10 eq 20
        ten - 10 eq 0
        ten * 2 eq 20
        ten / 2 eq 5
        ten % 30 eq 10
    }
}