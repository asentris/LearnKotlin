package classTypes.data

import atomicTest.eq

data class Book(val title: String, val authors: List<Author>)

data class Author(val name: String)

class BookExample {
    fun authorBooksMap(books: List<Book>): Map<Author, List<Book>> {
        val result = mutableMapOf<Author, MutableList<Book>>()
        for (book in books) {
            for (author in book.authors) {
                if (!result.containsKey(author)) {
                    result[author] = mutableListOf()
                }
                result.getValue(author) += book //this retrieves the mutable list, allowing the addition of more books
            }
        }
        return result
    }

    fun authorBooksMapNew(books: List<Book>): Map<Author, List<Book>> {
        val authors = books.flatMap { it.authors }.toSet()
        return authors.map { author -> author to books.filter { author in it.authors } }.toMap()
    }

    fun checkBookClass() {
        val books = listOf(
            Book("Computer Interfacing with Pascal & C", listOf(Author("Bruce Eckel"))),
            Book("Using C++", listOf(Author("Bruce Eckel"))),
            Book("C++ Inside & Out", listOf(Author("Bruce Eckel"))),
            Book("Blackbelt C++: The Masters Collection", listOf(Author("Bruce Eckel"))),
            Book("Thinking in C++: Introduction to Standard C++", listOf(Author("Bruce Eckel"))),
            Book(
                "Thinking in C++, Vol. 2: Practical Programming",
                listOf(Author("Bruce Eckel"), Author("Chuck Allison"))
            ),
            Book("Thinking in Java", listOf(Author("Bruce Eckel"))),
            Book("First Steps in Flex", listOf(Author("Bruce Eckel"))),
            Book("Atomic Scala", listOf(Author("Bruce Eckel"), Author("Dianne Marsh"))),
            Book("On Java 8", listOf(Author("Bruce Eckel"))),
            Book("Kotlin in Action", listOf(Author("Dmitry Jemerov"), Author("Svetlana Isakova"))),
            Book("Atomic Kotlin", listOf(Author("Bruce Eckel"), Author("Svetlana Isakova")))
        )
        val authorToBooksMap = authorBooksMap(books)
        val authorToBooksMapNew = authorBooksMapNew((books))
        authorToBooksMap.getValue(Author("Bruce Eckel")).size eq 11
        authorToBooksMap.getValue(Author("Svetlana Isakova")).first().title eq "Kotlin in Action"
        authorToBooksMapNew.getValue(Author("Bruce Eckel")).size eq 11
        authorToBooksMapNew.getValue(Author("Svetlana Isakova")).first().title eq "Kotlin in Action"
    }
}