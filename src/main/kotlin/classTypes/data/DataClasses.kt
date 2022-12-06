package classTypes.data

import atomicTest.eq

//must include var or val for constructor arguments
data class DataClasses(
    val a: Int,
    val b: Int,
) {
}

fun displayDataClass() {
    val one = DataClasses(1, 2)
    val two = DataClasses(1, 2)
    one eq "DataClasses(a=1, b=2)" //the data class puts the properties into a readable String
    one eq two //automatically makes instances with the same data equal
    one.copy() eq one //quick way to copy all the data
    one.copy(a = 2) eq DataClasses(2, 2) //copies data while accepting changes to specified properties
}
fun hashCodes(){
    val one = DataClasses(1,2)
    one.hashCode() eq 33 //creates hashcode based on data
    val map=HashMap<DataClasses,String>()
    map[one]="Hello"
    map[one] eq "Hello"
    val set=HashSet<DataClasses>()
    set.add(one)
    set.contains(one) eq true
}
//extension function that copies the instance but inserts new data
fun DataClasses.copyIt(newA: Int): DataClasses {
    return copy(a = newA)
}