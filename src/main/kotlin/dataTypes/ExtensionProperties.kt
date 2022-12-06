package dataTypes

import atomicTest.eq

class ExtensionProperties {
    val Int.extensionProperty: Double
        get():Double {
            return toDouble()
        }

    val <T> List<T>.firstOrNull: T?
        get() = if (isEmpty()) null else first()

    val List<*>.indices: IntRange //* -> called a star projection, all info about the type in the list is lost
        get() = 0 until size

    fun testExtensions() {
        10.extensionProperty eq 10.0

        listOf<Int>().firstOrNull eq null
        listOf("a", "b", "c").firstOrNull eq "a"

        listOf(1).indices eq 0..0
    }
}