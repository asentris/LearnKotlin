package other

import kotlin.math.PI as circleRatio
import kotlin.math.* //imports everything from this package

class Keywords {

    fun list(){
        /**
         * package
         * as
         * typealias
         * class
         * this
         * super
         * val
         * var
         * fun
         * for
         * null
         * true
         * false
         * is
         * in
         * throw
         * return
         * break
         * continue
         * object
         * if
         * try
         * else
         * while
         * do
         * when
         * interface
         * typeof
         */
    }
    //RANGE - an interval of values defined by a pair of endpoints
    // .. = 1..10 means from the number 1 up to and including 10
    // until = 1 until 10 means from the number 1 up to but not including 10 (cannot be used for floating points)
    val range1=1..10
    val range2=1 until 10

    //IntProgression - a type used to define ranges
    val rangeA:IntProgression=1..5
    val rangeB:IntProgression=1 until 5
    val rangeC:IntProgression=5 downTo 1
    val rangeD:IntProgression=1..10 step 2
    val rangeE:IntProgression=1 until 10 step 2
    val rangeF:IntProgression=10 downTo 1 step 2

    //as keyword allows user to import a package as a different name (see above)
    val myPI=circleRatio
    val myPIFullyQualified=kotlin.math.PI

    //<type parameter> - within the brackets declares a type parameter; List<Int> means a list of integer objects

    //DISPLAY AN OBJECT'S CLASS:
    //println("${objectName::class.simpleName}")    OR
    //println("${objectName::class.qualifiedName}")
}