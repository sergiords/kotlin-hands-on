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
