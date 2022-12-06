package expressions

import atomicTest.eq

//tests whether something is within a range
//!in tests if something is not within a range
//(within a for loop, it is used for iteration)
class InExpressions {
    fun inExpressions() {
        (12 in 1..10) eq "false"
        (10.5 in 1.0..10.0) eq "false"
        ("af" in "aa".."az") eq "true"
        ('c' in 'a'..'z') eq "true"
        ('c' !in 'a'..'z') eq "false"
    }
}