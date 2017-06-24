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
