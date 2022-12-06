package sequences

import atomicTest.eq

class GenerateSequences {
    //sequences can only be run ONE TIME
    fun generateSequences() {
        val x = mutableListOf("A", "X", "B")
        //generateSequence() produces values from the argument value until the first null
        val untilNull = generateSequence(12) { it + 1 }
        val untilX = generateSequence { x.removeAt(0).takeIf { it != "X" } } //takeIf() returns null if false
        val until1 = generateSequence(6) { seq -> (seq - 1).takeIf { it > 0 } }.toList()

        untilNull.first() eq 12 //last() would cause the program to hang
        untilX.toList() eq listOf("A")
        x eq mutableListOf("B")
        until1 eq listOf(6, 5, 4, 3, 2, 1)
    }

    fun randomExamples() {
        data class Instructor(val name: String)

        data class Student(val name: String)

        data class Lesson(
            val instructor: Instructor,
            val students: Set<Student>,
            val rating: Map<Student, Int>
        )

        data class School(
            val instructors: Set<Instructor>,
            val students: Set<Student>,
            val lessons: Sequence<Lesson>
        )

        val bob = Instructor("Bob")
        val jill = Instructor("Jill")
        val timmy = Student("Timmy")
        val sally = Student("Sally")
        val mary = Student("Mary")
        val jack = Student("Jack")
        val math = Lesson(bob, setOf(timmy, sally), mapOf(timmy to 1, sally to 4))
        val english = Lesson(jill, setOf(timmy, mary), mapOf(timmy to 3, mary to 2))
        val history = Lesson(jill, setOf(timmy, sally), mapOf(timmy to 3, sally to 5))
        val school = School(
            setOf(bob, jill),
            setOf(timmy, sally, mary, jack),
            sequenceOf(math, english, history)
        )

        fun School.studentInstructors(
            student: Student
        ): Set<Instructor> {
            return lessons.filter { student in it.students }
                .map { it.instructor }.toSet()
        }

        fun School.studentsOf(
            instructor: Instructor
        ): Set<Student> {
            return lessons.filter { it.instructor == instructor }
                .flatMap { it.students }.toSet()
        }

        fun School.averageInstructorRating(instructor: Instructor): Double =
            lessons.filter { instructor == it.instructor }
                .flatMap { it.rating.values }
                .average()

        fun School.favoriteInstructor(student: Student): Instructor? =
            lessons.filter { student in it.students }
                .groupBy { it.instructor }
                .maxByOrNull { it.value.size }
                ?.key

        school.studentInstructors(sally) eq setOf(bob, jill)
        school.studentInstructors(timmy) eq setOf(bob, jill)
        school.studentsOf(bob) eq setOf(timmy, sally)
        school.studentsOf(jill) eq setOf(timmy, mary, sally)
        school.averageInstructorRating(bob) eq 2.5
        school.favoriteInstructor(jack) eq null
        school.favoriteInstructor(timmy) eq jill

        fun fibonacci(): Sequence<Int> {
            var previous = 1
            return generateSequence(0) {
                val current = it + previous
                previous = it
                current
            }
        }
    }
}