package fr.xebia.xke

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
