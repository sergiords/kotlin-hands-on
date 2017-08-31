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

class StandardPrice(val value: Int) : Price

class PromotionalPrice(val value: Int, val discount: Int) : Price

// TODO return value for standard prices and (value - discount) for promotional prices, otherwise 0
fun computePrice(price: Price): Int =
    when (price) {
        is StandardPrice -> price.value
        is PromotionalPrice -> price.value - price.discount
        else -> 0
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
// TODO return last url parameter converted to an Int or null if param is not found or not a valid number
fun convertURLParam(url: String): Int? {
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
// TODO return price.value.toLong(), mind references nullability, 0 if all references are null
fun convertPriceToLong(price: StandardPrice?): Long {
    return price?.value?.toLong() ?: 0
}


/**
 * Ranges
 *
 * You can generate ranges with '..' operator
 *  val oneToTen: IntRange = 1..10
 *
 * You can also generate ranges using any range type (IntRange, LongRange, ClosedRange<T>, ...)
 *  val oneToTen: IntRange = IntRange(1, 10)
 */
// TODO generate a list of integers from 1 to max number using ranges
fun generateIntegerList(max: Int): List<Int> = IntRange(1, max).toList()

/**
 * Ranges: Progressions
 *
 * Ranges rely on *Progression and Kotlin provide useful operations on *Progressions like steps
 */
// TODO generate a range from 0 to max and sum all numbers which are dividers of 3
fun sumSequenceNumbers(max: Int): Int = (0..max).step(3).sum()


/**
 * Loops
 *
 * for loop iterates through anything that provides an iterator:
 *  for (item in items) { ... }
 *
 * Ranges can also be used in for loops:
 *  for (i in 1..10) { ... }
 *  for (i in 1..10 step 2) { ... }
 *  for (i in 10 downTo 1) { ... }
 *  for (i in 10 downTo 1 step 2) { ... }
 */
// TODO compute prices total amount, if 2 or more prices are promotional prices, apply a 5.0 discount to total amount
fun computeTotalPrice(prices: List<Price>): Int {
    var sum = 0
    var promotions = 0
    for (price in prices) {
        sum += computePrice(price)
        if (price is PromotionalPrice) {
            promotions++
        }
    }
    if (promotions >= 2) {
        sum -= 5
    }
    return sum
}
