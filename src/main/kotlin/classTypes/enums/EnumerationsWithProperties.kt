package classTypes.enums

import atomicTest.eq

enum class Directions(val notation: String) {
    North("N"),
    South("S"),
    East("E"),
    West("W"), ; //semicolon required

    val opposite: Directions
        get() = when (this) {
            North -> South
            South -> North
            East -> West
            West -> East
        }
}
fun callDirections(){
    Directions.North.notation eq "N"
    Directions.North.opposite eq Directions.South
    Directions.West.opposite.opposite eq Directions.West
    Directions.North.opposite.notation eq "S"
}