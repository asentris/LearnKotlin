package containers

import Section
import atomicTest.capture
import atomicTest.eq
import runAll

//Maps connect keys to values and looks up the value when given the key
//mapOf() and mutableMapOf() preserve the order that pairs are placed into them
//a map returns no if it doesn't contain an entry for a given key

class Maps {
    private fun readOnlyMap() {
        val readOnlyMap = mapOf("Pi" to 3.1415, "e" to 2.718, "phi" to 1.618)

        readOnlyMap eq "{Pi=3.1415, e=2.718, phi=1.618}"
        readOnlyMap.entries eq "[Pi=3.1415, e=2.718, phi=1.618]"
        readOnlyMap["e"] eq 2.718
        readOnlyMap.keys eq setOf("Pi", "e", "phi")
        readOnlyMap.values eq "[3.1415, 2.718, 1.618]"

        fun iterateOverMap() {
            var s = ""
            for (i in readOnlyMap) {
                s += "${i.key}=${i.value} "
            }
            s eq "Pi=3.1415 e=2.718 phi=1.618"
        }
        iterateOverMap()

        fun iterateOverMap2() {
            var s = ""
            for ((key, value) in readOnlyMap) {
                s += "$key=$value "
            }
            s eq "Pi=3.1415 e=2.718 phi=1.618"
        }
        iterateOverMap2()

        fun returnNonNullForBlankEntry() {
            val blankMap = mapOf<String, Int>()
            blankMap["abc"] eq null
            capture { blankMap.getValue("abc") } eq "NoSuchElementException: " +
                    "Key abc is missing in the map."
            blankMap.getOrDefault("abc", 0) eq 0
        }
        returnNonNullForBlankEntry()
    }

    private fun mutableMap() {
        val mutableMap = mutableMapOf<String, Int>("One" to 1)
        val emptyMap = mutableMapOf<Int, String>()

        mutableMap["One"] = 2
        mutableMap["Three"] = 3
        mutableMap += "Five" to 5

        mutableMap eq mutableMapOf("One" to 2, "Three" to 3, "Five" to 5)
        emptyMap.withDefault { "a" } eq mutableMapOf()

        println("\nLazy map example")
        fun lazyMapExample() {
            fun <P, L> lazyMap(initializer: (P) -> L): Map<P, L> {
                val map = mutableMapOf<P, L>() //map is always empty, so it will always call withDefault()
                return map.withDefault { key -> //key == 1 (integer)
                    val newValue = initializer(key) //initializer(key) == "1"
                    map[key] = newValue
                    return@withDefault newValue
                }
            }

            val intStringMap: Map<Int, String> =
                lazyMap { parameters ->
                    return@lazyMap parameters.toString()
                }

            intStringMap.getValue(1)
        }
        lazyMapExample()
    }

    private fun mapObjects() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val bill = Person("Bill", 20)
        val chad = Person("Chad", 20)
        val devin = Person("Devin", 24)
        val dustin = Person("Dustin", 30)
        val nameList = listOf("Bill", "Chad", "Devin", "Dustin")
        val ageList = listOf(20, 20, 24, 30)

        val personList = ageList.zip(nameList) { a, n -> Person(n, a) }
        personList eq listOf(bill, chad, devin, dustin)

        println("\nManipulating maps examples:")
        fun mapWithInstances() {
            val personMap = mutableMapOf(bill.age to bill, 1 to bill, devin.age to devin)
            personMap[20] eq bill
            personMap.get(20) eq bill
            personMap.getValue(20).name eq "Bill"
            personMap.getOrElse(999) { "*null*" } eq "*null*"
            personMap.getOrDefault(999, bill) eq bill
            personMap.filterKeys { it == 1 } eq mapOf(1 to bill)
            personMap.filterValues { it == bill } eq mapOf(1 to bill, 20 to bill)
        }
        mapWithInstances()

        println("\nMaking maps with groupBy():")
        fun groupMapWithGroupBy() { //function == key; listOf(all matched objects) == value
            personList[1] eq chad
            personList.get(1) eq chad
            personList[1].name eq "Chad"

            val mapByAge: Map<Int, List<Person>> = personList.groupBy(Person::age)
            val mapByAge2: Map<Int, List<Person>> = personList.groupBy { it.age }
            val mapByFirstLetter = personList.groupBy { it.name.first() }
            mapByAge eq mapOf(20 to listOf(bill, chad), 24 to listOf(devin), 30 to listOf(dustin))
            mapByAge eq mapByAge2
            mapByAge[20] eq listOf(bill, chad)
            mapByFirstLetter['D'] eq listOf(devin, dustin)
        }
        groupMapWithGroupBy()

        println("\nMaking maps with filter():")
        fun groupMapWithFilter() { //ineffective
            val listByAge20 = personList.filter { it.age == 20 }
            val listByAge22 = personList.filter { it.age == 22 }
            val mapByAge = mapOf(20 to listByAge20, 22 to listByAge22)
            mapByAge[20] eq listOf(bill, chad)
        }
        groupMapWithFilter()

        println("\nMaking maps with associate...():")
        fun groupWithAssociate() {
            //it == key; function == value
            val mapByAgeWith: Map<Person, Int> = personList.associateWith { it.age }
            //it == value; function == key (the opposite of associateWith)
            //values must be unique in the list, or the last one will be the only one associated with the key
            val mapByAgeBy: Map<Int, Person> = personList.associateBy { it.age }

            mapByAgeWith[bill] eq 20
            mapByAgeBy[20] eq chad //chad replaces bill, since both have an age of 20
        }
        groupWithAssociate()

        println("\nTransforming a map with map...():")
        fun transformingMap() {
            val mapByAge = personList.groupBy { it.age }
            mapByAge.map { it.value } eq listOf(listOf(bill, chad), listOf(devin), listOf(dustin))
            //duplicate keys will be overridden
            mapByAge.mapKeys { it.key + 20 } eq mapOf(
                40 to listOf(bill, chad),
                44 to listOf(devin),
                50 to listOf(dustin)
            )
            mapByAge.mapValues { it.value.first() } eq mapOf(20 to bill, 24 to devin, 30 to dustin)
        }
        transformingMap()

        println("\nExplanation of getOrPut():")
        fun getOrPutExamples() {
            //getOrPut() returns the value for the given key. if the key is not found in the map,
            //defaultValue function is called and its result is put into the map under the given key
            //the result of defaultValue function is returned
            //in the cases below, "list" is a reference to a particular mutableListOf() object
            //when "list" is updated, the object it references (which is also in "result") is updated
            val mapByAge = personList.groupBy { it.age }
            val stringList = listOf("Aa", "Ba", "Bb", "Ca")
            val stringMap = mapOf(
                'A' to listOf("Aa"),
                'B' to listOf("Ba", "Bb"),
                'C' to listOf("Ca"),
            )

            fun <T, R> List<T>.groupByAge(f: (T) -> R): Map<R, List<T>> {
                val result = mutableMapOf<R, MutableList<T>>()
                for (element in this) {
                    val list = result.getOrPut(f(element)) { mutableListOf() }
                    list.add(element)
                }
                return result
            }

            fun <T, R> List<T>.groupByString(f: (T) -> R): Map<R, List<T>> {
                val result = mutableMapOf<R, MutableList<T>>()
                for (element in this) {
                    val list = result.getOrPut(f(element)) { mutableListOf() }
                    list.add(element)
                }
                return result
            }

            personList.groupBy(Person::age) eq mapByAge
            personList.groupByAge(Person::age) eq mapByAge
            stringList.groupBy { it.first() } eq stringMap
            stringList.groupByString { it.first() } eq stringMap
        }
        getOrPutExamples()
    }

    operator fun invoke() {
        runAll(
            "Map examples",
            Section("Read only maps", ::readOnlyMap),
            Section("Mutable maps", ::mutableMap),
            Section("Mapping objects", ::mapObjects),
        )
    }
}