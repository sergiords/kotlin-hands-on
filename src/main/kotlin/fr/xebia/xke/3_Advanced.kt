package fr.xebia.xke

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Operator overloading
 *
 * Predefined operators +, -, ... can be overloaded for custom types.
 * To do so, define a method with proper name (plus, minus, ...) and 'operator' modifier.
 */
val v1 = Vector(1, 2)
val v2 = Vector(3, 5)

val v3 = v1 + v2
val v4 = v1 - v2

data class Vector(val dx: Int, val dy: Int) {

    operator fun plus(that: Vector): Vector = Vector(this.dx + that.dx, this.dy + that.dy)

    operator fun minus(that: Vector): Vector = Vector(this.dx - that.dx, this.dy - that.dy)

}


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
val squareArea = 10.square()

fun Int.square() = this * this

/**
 * Same here but with any type
 */
val ofCourse = "Some String".isString()
val noWay = 100.isString()

fun <T> T.isString(): Boolean = this is String


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

val Temperature.kelvin: Int
    get() = 273 + this.celsius // approximately

val celsiusValue: Int = Temperature(10).celsius // 10 °C
val kelvinValue: Int = Temperature(10).kelvin   // 283 °K


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

fun build(name: String, init: Build.() -> Unit): Build {
    val build = Build(name)
    build.init()
    return build
}

val myBuild =
    build("myProject") {
        dependency("libA")
        dependency("libB")
    }

/**
 * Destructuring declarations
 * We have seen in Intermediate part that we can destruct an object
 * See below how to make an object destructible
 *
 * Destructuring declaration can be used on Maps and Lambda since v1.1
 */
/**
 * TODO: make the following code compile by modifying MyTime class declaration
 */
data class MyTime(val hour: Int, val minute: Int)

fun getPrettyTime(myTime: MyTime): String {
    val (hour, minute) = myTime
    return "$hour:$minute"
}


/**
 * Inline functions
 *
 * Inlining functions allow compiler to emit inlined function body at each call-site
 *
 * https://kotlinlang.org/docs/reference/inline-functions.html
 */
inline fun Logger.fineIfEnabled(message: () -> String) {
    if (this.isLoggable(Level.FINE)) {
        this.fine(message())
    }
}

/**
 * Inline functions also allows access to parameter types since function code is emitted at call-site
 *
 * Parameter Types must be marked as 'reified'
 *
 * To access a type's class use the '::' operator like: 'String::class'
 */
inline fun <reified T> parameterTypeClass(): Class<T> = T::class.java
