package dataTypes

import atomicTest.eq

class Types{
    val million=1_000_000 //kotlin allows underscores for readability
    val numerator=19
    val denominator=10
    val modulus=numerator%denominator //9
    val divideInteger=numerator/denominator //1 (integer division truncates result)

    //Integer values between -231 to +231-1 (a constraint of 32 bits)
    //Int.MAX_VALUE is the largest value an integer can hold (Long.MAX_VALUE applies to longs)
    //Long values between -263 to +263-1
    val myLong=2L //long type
    val myLong2:Long=2 //long type

}