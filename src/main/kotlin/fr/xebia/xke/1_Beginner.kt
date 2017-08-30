package fr.xebia.xke

/**
 * Properties
 *
 * var name: Type = ... // read-write
 *
 * val name: Type = ... // read-only once initialized
 *
 * const val name: Type = ... // when value is known at compile-time
 *
 * val x = 10 // Type is optional when it can be inferred
 */
val host: String = "xebia.fr"

/**
 * Kotlin has common types like Boolean, Int, Long, Float, Double, ...
 *
 * Kotlin has NO primitive types like in Java (int, long, ...).
 */
val port: Int = 443

/**
 * Kotlin has String-templates for property interpolation.
 *
 * "Hello $userName, today is ${localDate.format(...)}"
 */
val url: String = "https://blog.$host:$port"

/**
 * Functions... are fun!
 *
 * fun name(argName: ArgType): ReturnType = ... // function with an expression body
 *
 * fun name(argName: ArgType): ReturnType {...} // function with a body
 *
 * fun name(argName: ArgType) {...} // function with no return (same as returning Unit)
 */
fun isSecured(url: String): Boolean = url.startsWith("https://")

/**
 * Conditional expressions
 *
 * val result = if (...) someValue else if (...) otherValue else defaultValue
 */
fun factorial(n: Int): Int = if (n <= 1) 1 else n * factorial(n - 1)

/**
 * When expressions
 *
 * when {
 *   x < 0 -> -x
 *   x = 0 -> 0
 *   else -> x
 * }
 */
fun remainingTime(durationInSeconds: Int): String = when {
    durationInSeconds in 0..59 -> "$durationInSeconds second(s)"
    durationInSeconds / 60 in 0..59 -> "${durationInSeconds / 60} minute(s)"
    durationInSeconds / 60 / 60 in 0..23 -> "${durationInSeconds / 60 / 60} hour(s)"
    else -> "${durationInSeconds / 60 / 60 / 24} day(s)"
}

/**
 * Lambdas
 *
 * An anonymous function can be typed and declared using following syntax:
 *
 * val isStrictlyPositive : (Int) -> Boolean = { it -> it > 0 }
 *
 * Lambdas can of course be passed as function parameters
 *
 * names.filter({ it -> it.name == searchedName })
 *
 */
val pair: (Int) -> Boolean = { number -> number % 2 == 0 }


/**
 * When lambda has only one parameter it can be ommited and 'it' is the default name:
 *  { it > 0 }
 * When lambda has more than one parameters you must give them proper names:
 *  { name, age -> "$name is $age year(s) old" }
 */
val product: (Int, Int) -> Int = { x, y -> x * y }


/**
 * Type checks and Smart Casts
 *
 * We can check whether an object conforms to a given type by using the 'is' operator or its negated form '!is'.
 * It allows using smart casts (no cast in branches where type can be guessed)
 *
 * fun size(any: Any): Int = if (any is String) any.length else if (any is List<*>) any.size else -1
 */
interface Price

class StandardPrice(val value: Double) : Price

class PromotionalPrice(val value: Double, val discount: Double) : Price

fun computePrice(price: Price): Double =
    when (price) {
        // TODO return value for standard prices and (value - discount) for promotional prices, otherwise 0
        is StandardPrice -> price.value
        is PromotionalPrice -> price.value - price.discount
        else -> 0.0
    }


/**
 * Null references
 *
 * Kotlin references are not-null by default
 * To allow null references one must suffix type with '?'
 *
 * val name: String? = null
 *
 * For strings it is possible to use for example toIntOrNull
 * to convert un String to Integer
 *
 */
fun convertURLParam(url: String): Int? {
    // TODO return last url parameter converted to an Int or null if param is not found or not a valid number
    val urlPar = url.split("=")
    if (urlPar.size > 1) {
        return urlPar[urlPar.size - 1].toIntOrNull()
    }
    return null
}

/**
 * Null references: Elvis operator
 *
 * To avoid super classic if (.. != null) structures Kotlin has built-in Elvis operator
 *  val user : User? = ...
 *  val city: String = user?.address?.city ?: "Unknown"
 */
fun convertPriceToInt(price: StandardPrice?): Int {
    // TODO return price.value.toInt(), mind references nullability, 0 if all references are null
    return price?.value?.toInt() ?: 0
}


/**
 * generating ranges with .. you can use too a range to generate ranges of any type (IntRange)
 */
fun generateIntegerList(maxNumber: Int): List<Int> = IntRange(1, maxNumber).toList()
//TODO generate a list of integers from 1 to max number


/**
 * Sum all consecutive number with a step of 3 (position 3,6,9,....)
 * @max max number of the sequence
 * @return the value of the sum
 */

fun sumSequenceNumbers(max: Int): Int = (0..max step 3).sum()
//TODO generate sequence from 0 to max and sum each 3 position


/**
 * for loop iterates through anything that provides an iterator.
 * Try to use it.
 * @products list of products to process
 * @propotional amount of items to process before apply policy of additional 20%
 */

fun computeTotalPrice(products: List<Product>, promotional: Int): Double {
    //TODO calculate the total proce of the product list. If more than *promotional* items have a promotional price, apply policy of 20% additional discount for all the products
    var sum = 0.0
    var amount = 0
    for (item in products) {
        sum += computePrice(item.price)
        if (item.price is PromotionalPrice)
            amount++
    }
    if (amount >= promotional) {
        sum *= 0.8
    }
    return sum
}


/**
 * Algorithme
 * 1. Add a 0 to the end and multiply x2 all numbers in a pair position starting by the right
 * 2. If the number is >=10 sum both digits (f.e 10 ==1+0=1 11=1+1=2)
 * 3. sum all the numbers
 * 4. if total is multiple of 10 return 0, else return 10 - sum (https://fr.wikipedia.org/wiki/Formule_de_Luhn)
 */
fun computeControlNumberSiren(siren: List<Int>): Int {
//TODO compute the numbers of the list to calculate the last number ot the luhn algorithme
    val reversed = siren.reversed()
    val sum = (0..siren.size - 1)
        .sumBy {
            println("computing "+reversed[it]+" it="+it)
            if (it % 2 == 0) {
                val x2 = reversed[it] * 2
                println("multiply " + reversed[it] +" getting $x2")
                x2 % 10 + (x2 / 10)
            } else
                reversed[it]
        }
    if (sum % 10 == 0) {
        return 0
    }
    return 10 - sum % 10
}


class Product(val name: String, val price: Price)
