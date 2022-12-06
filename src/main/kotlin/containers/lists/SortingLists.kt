package containers.lists

import Section
import atomicTest.eq
import runAll
import kotlin.reflect.KProperty1

class SortingLists {
    /**     ascending       =       false -> true,      1 -> 9,     a -> z,     null -> non-null    */
    private val m = Product("m", "h", 100.00, 1, 2050, false, emptyList())
    private val a = Product("a", null, 50.00, 1, 2100, true, emptyList())
    private val g = Product("g", "w", 100.00, 4, null, true, emptyList())
    private val c = Product("c", null, 150.00, 8, null, false, emptyList())

    private val productList: List<Product> = listOf(m, a, g, c)

    private fun simpleSorting() {
        productList.sortedWith(compareBy(Product::details, Product::expirationYear)) eq
                listOf(c, a, m, g)
        productList.sortedWith(compareBy(Product::onSale, Product::details, Product::name)) eq
                listOf(c, m, a, g)
        productList.sortedWith(compareByDescending(Product::details)) eq
                listOf(g, m, a, c)
    }

    private fun sortProducts(list: List<Product>, comparators: List<Comparator<Product>>): List<Product> {
        var newList: List<Product> = list
        var oldComparator: Comparator<Product> = compareBy { "" }
        comparators.forEachIndexed { index, comparator ->
            if (index == 0) {
                newList = list.sortedWith(comparator)
                oldComparator = comparator
            } else newList = list.sortedWith(oldComparator.then(comparator).apply { oldComparator = this })
        }
        return newList
    }

    /** This is unable to effectively sort nullsLast() */
    private fun sortWithoutNullsLast() {
        /** SEALED CLASS LAMBDAS */
        fun compareBySealedLambda(orderAscend: Boolean, productSealedLambda: ProductSealedLambda): Comparator<Product> =
            when (orderAscend) {
                true -> compareBy(productSealedLambda.lambda) //switch between lambda or parenthesis
                false -> compareByDescending(productSealedLambda.lambda)
            }

        val sealedComp1 = compareBySealedLambda(true, ProductSealedLambda.Details)
        val sealedComp2 = compareBySealedLambda(false, ProductSealedLambda.Inventory)
        val sealedCompList1 = listOf(sealedComp1, sealedComp2)
        val sealedCompList2 = listOf(sealedComp2, sealedComp1)
        sortProducts(productList, sealedCompList1) eq listOf(c, a, m, g)
        sortProducts(productList, sealedCompList2) eq listOf(c, g, a, m)

        /** ENUM CLASS LAMBDAS */
        fun compareByEnumLambda(orderAscend: Boolean, productEnumLambda: ProductEnumLambda): Comparator<Product> =
            when (orderAscend) {
                true -> compareBy(productEnumLambda.lambda)
                false -> compareByDescending(productEnumLambda.lambda)
            }

        val enumComp1 = compareByEnumLambda(true, ProductEnumLambda.DETAILS)
        val enumComp2 = compareByEnumLambda(false, ProductEnumLambda.INVENTORY)
        val enumCompList1 = listOf(enumComp1, enumComp2)
        val enumCompList2 = listOf(enumComp2, enumComp1)
        sortProducts(productList, enumCompList1) eq listOf(c, a, m, g)
        sortProducts(productList, enumCompList2) eq listOf(c, g, a, m)
    }

    private fun sortWithNullsLast() {
        /** SEALED CLASS KPROPERTY1 */
        fun createComparators(productOrderList: List<Pair<OrderType, ProductEnumKProp>>):
                List<Comparator<Product>> =
            productOrderList.map { pair ->
                val order = pair.second
                if (pair.first is OrderType.Ascending) when (order.type) {
                    ProductTypes.STRING_NULL -> compareBy(nullsLast(), order.kStringNull!!)
                    ProductTypes.INT -> compareBy(nullsLast(), order.kInt!!)
                } else when (order.type) {
                    ProductTypes.STRING_NULL -> compareByDescending(order.kStringNull!!)
                    ProductTypes.INT -> compareByDescending(order.kInt!!)
                }
            }

        val pair1 = Pair(OrderType.Ascending, ProductEnumKProp.Details)
        val pair2 = Pair(OrderType.Descending, ProductEnumKProp.Inventory)
        val comparators1 = createComparators(listOf(pair1, pair2))
        val comparators2 = createComparators(listOf(pair2, pair1))
        sortProducts(productList, comparators1) eq listOf(m, g, c, a)
        sortProducts(productList, comparators2) eq listOf(c, g, m, a)
    }

    init {
        runAll(
            "Sorting Strings",
            Section("Simple sorting", ::simpleSorting),
            Section("Sort without nullsLast capability", ::sortWithoutNullsLast),
            Section("Sort with nullsLast", ::sortWithNullsLast)
        )
    }
}

data class Product(
    val name: String,
    val details: String?,
    val price: Double,
    val inventory: Int,
    val expirationYear: Long?,
    val onSale: Boolean,
    val distributors: List<Distributor>,
)

data class Distributor(
    val name: String,
    val estYear: Int,
)

enum class ProductEnumLambda(val lambda: (Product) -> Comparable<*>?) {
    DETAILS(Product::details),
    INVENTORY(Product::inventory),
}

sealed class ProductSealedLambda(val lambda: (Product) -> Comparable<*>?) {
    object Details : ProductSealedLambda(Product::details)
    object Inventory : ProductSealedLambda(Product::inventory)
}

enum class ProductEnumKProp(
    val type: ProductTypes,
    val kStringNull: KProperty1<Product, String?>? = null,
    val kInt: KProperty1<Product, Int>? = null,
) {
    Details(ProductTypes.STRING_NULL, kStringNull = Product::details),
    Inventory(ProductTypes.INT, kInt = Product::inventory),
}

enum class ProductTypes {
    STRING_NULL, INT,
}

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}