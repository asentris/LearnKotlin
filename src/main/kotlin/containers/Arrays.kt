package containers

//Arrays are always mutable

val implicitArray = arrayOf(2, 3)
val explicitArray: Array<Int> = arrayOf()
val explicitArray2 = arrayOf<Int>()

//the spread operator '*' is only used for Arrays
val sampleList = listOf(2, 3)
val spreadOperator = arrayOf(1, *implicitArray, 4, 5)
val spreadOperator2 = intArrayOf(1, *sampleList.toIntArray(), 4, 5)