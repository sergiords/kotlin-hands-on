@file:Suppress("UNREACHABLE_CODE", "UNUSED_PARAMETER", "UnusedImport", "unused")

package fr.xebia.xke

import kotlinx.coroutines.experimental.delay
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.coroutines.experimental.buildSequence

/**
 * Operator overloading
 *
 * Predefined operators +, -, ... can be overloaded for custom types.
 * To do so, define a method with proper name (plus, minus, ...) and 'operator' modifier.
 */
val v1 = Vector(1, 2)
val v2 = Vector(3, 5)

fun v1plusV2() = v1 + v2
fun v1minusV2() = v1 - v2

data class Vector(val dx: Int, val dy: Int) {

    // TODO implement this operator overloading function to return the result of a Vector addition
    operator fun plus(that: Vector): Vector = TODO()

    // TODO implement this operator overloading function to return the result of a Vector substraction
    operator fun minus(that: Vector): Vector = TODO()

}

/**
 * Coroutines
 *
 * Coroutines (like async/await in Javascript) allow using non blocking calls (suspending) in a classic blocking style
 * Suspending model also allows Generators (lazy computations) to be written in a more classic style
 *
 * Suspension happens when calling a function marked with 'suspend' modifier
 *
 * Learn more: https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md
 * For the brave: https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md
 */
// TODO implement a Fibonacci sequence generator using yield suspending function
fun fibonacciSeq(): Sequence<Int> = buildSequence {

    TODO("yield first value here")

    while (true) {
        TODO("compute and yield next values here")
    }
}

fun fiveFirstFibonacci(): Sequence<Int> = fibonacciSeq().take(5) // values are yielded only when this sequence is iterated

/**
 * Coroutines: Non Blocking
 *
 * Suspending functions can only be called within special coroutine-handling functions (see links above for details)
 *  async(CommonPool) {
 *      delay(1000)
 *      println("Hello") // printed last
 *  }
 *  println("Goodbye") // printed first
 */
fun giveTreatment(): String {
    // TODO return 'aspirin' after a delay of 1 second (use delay suspending function and suspend modifier)
    TODO()
}
/**
 * NB: Promise coroutines can be cancel at any moment by calling promise.cancel()
 */


/**
 * Function extension
 *
 * A function can be attached to an existing type
 *
 * fun String.hello() = "Hello $this"
 *
 * "Bob".hello() // prints "Hello Bob"
 */
val squareSize = 10
fun squareArea() = 10.square()

// TODO implement this function extension which computes the square value of the Int it refers to (receiver type)
fun Int.square(): Int = TODO()

/**
 * Same here but with any type
 */
fun ofCourse() = "Some String".isString()
fun noWay() = 100.isString()

// TODO implement this function extension which returns true if its receiver type is a String
fun <T> T.isString(): Boolean = TODO()


/**
 * Property extension
 *
 * A property can also be attached to an existing type
 *
 * val String.magicLength
 *  get() = this.length * 14
 *
 * "Bob".magicLength // equals to 42
 */

data class Temperature(val celsius: Int)

// TODO implement this property extension to define the temperature in Kelvin (~ 273° + celsius) (a getter may be useful)
val Temperature.kelvin: Int
    get() = TODO() // approximately

fun celsiusValue(): Int = Temperature(10).celsius // 10 °C
fun kelvinValue(): Int = Temperature(10).kelvin   // 283 °K


/**
 * Function type with receiver is almost like function extension but can be used as function parameters only
 *
 * fun test(init: String.() -> Unit) = { ... }
 *
 * test {
 *  "Something".init()
 * }
 *
 * This allows creating type-safe builders/DSLs
 */
data class Build(val name: String, private val dependencies: ArrayList<String> = arrayListOf()) {
    fun dependency(name: String) = dependencies.add(name)
}

// TODO implement this function which has a function type with receiver parameter to create a build and initialize it with init
fun build(name: String, init: Build.() -> Unit): Build {
    TODO()
}

fun myBuild() =
    build("myProject") {
        dependency("libA")
        dependency("libB")
    }


/**
 * Inline functions
 *
 * Inlining functions allow compiler to emit inlined function body at each call-site
 *
 * https://kotlinlang.org/docs/reference/inline-functions.html
 */
// TODO only log at FINE level when message is loggable at FINE level
inline fun Logger.fineIfEnabled(message: () -> String) {
    TODO()
}

/**
 * Inline functions also allows access to parameter types since function code is emitted at call-site
 *
 * Parameter Types must be marked as 'reified'
 *
 * To access a type's class use the '::' operator like: 'String::class'
 */
// TODO return the Class corresponding to function's parameter type
inline fun <reified T> parameterTypeClass(): Class<T> = TODO()


/**
 * Destructuring declarations
 *
 * We have seen in Intermediate part that we can destruct an object
 * See below how to make an object destructible
 *
 * Destructuring declaration can be used in Lambdas since v1.1, examples:
 *
 *  data class User(val name: String, val age: Int)
 *
 *  val (name, age) = User("Bob", 30)
 *
 *  users.filter { (name, age) -> name.startsWith("Bob") && age >= 20 }
 *  users.filter { (name, _) -> name.startsWith("Bob") }
 *
 *  mapOf<String, Int>(...).forEach { (key, value) -> ... }
 *
 */
data class MyTime(val hour: Int, val minute: Int)

// TODO return a list of formatted times 'hour:minute' using destructuring declaration
fun formatTimes(times: List<MyTime>): List<String> = TODO()

