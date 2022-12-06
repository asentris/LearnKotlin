package expressions

import atomicTest.eq

//expressions can be assigned to val and var, since they have return values (println(), if expressions, i++, etc.)
//this is in contrast to statements
class OtherExpressions {
    var a = 0
    var b = 0
    fun trueOrFalse(){
        (a != b) eq "false"
        (a < 10 || b > 10) eq "true"
        (a < 10 && b > 10) eq "false"
    }
    fun operatorExpressions(){
        a++ eq 0
        a eq 1
        ++a eq 2
        b-- eq 0
        b eq -1
        --b eq -2
    }
}