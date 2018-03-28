package fr.xebia.xke

import arrow.core.*

import arrow.core.Either
import arrow.core.flatMap
import arrow.data.NonEmptyList
import arrow.data.Validated
import arrow.data.applicative
import arrow.data.ev
import arrow.syntax.applicative.tupled

/**
 * This class validates user data in two ways:
 * - Applicative for all fields (validated all fields)
 * - Monadic within each field (stops on the first error per field)
 */
class UserValidation {

    /*
     * The following functions might help you
     * - Option.fromNullable("something")
     * - Option(maybeRight).toEither { "Left value" }
     * - Either.cond(true, { 42 }, { "Error" })
     * - Validated.fromEither(...)
     * - Validated.applicative<List<String>>()
            .map2(validated1, validated2, { v: Tuple<Int, Int> -> v.a + v.b })
            .ev()
     * - Validated.applicative<NonEmptyList<String>>()
            .tupled(validated1, validated2, ..., validatedN)
            .ev()
            .map{ v: Tuple<Int, Int> -> v.a + v.b }
     * - NonEmptyList.of("a string", "another string")
     */

    // TODO find a way to aggregate in an applicative way the 'readName' and 'readAge' functions
    fun validateUser(params: Map<String, String>): Validated<NonEmptyList<String>, User>  {
        return Validated.applicative<NonEmptyList<String>>()
            .tupled(
                Validated.fromEither(readName(params)),
                Validated.fromEither(readAge(params)))
            .ev()
            .map { z: Tuple2<String, Int> -> User(z.a, z.b) }
    }

    // TODO read the field 'name' and validate that is non blank
    fun readName(params: Map<String, String>): Either<NonEmptyList<String>, String> =
        getValue("name", params)
            .flatMap { nonBlank ("name", it) }

    // TODO read the field 'age' and validate that is non blank, valid int, and non negative
    fun readAge(params: Map<String, String>): Either<NonEmptyList<String>, Int> =
        getValue("age", params)
            .flatMap { nonBlank ("age", it) }
            .flatMap { parseInt ("age", it) }
            .flatMap { nonNegative (it) }

    // TODO get a value from the map or return an error
    fun getValue(name: String, data: Map<String, String>): Either<NonEmptyList<String>, String> =
        data[name]?.let { Either.right(it) } ?: Either.left(NonEmptyList.of("$name is not present"))

    fun parseInt(name: String, data: String): Either<NonEmptyList<String>, Int> =
        try { Either.right(data.toInt()) }
        catch(e: NumberFormatException) {
            Either.left(NonEmptyList.of("$name must be an integer"))
        }

    fun nonBlank(name: String, data: String): Either<NonEmptyList<String>, String> =
        if (data.isNotEmpty()) Either.right(data)
        else Either.left(NonEmptyList.of("$name is blank"))

    fun nonNegative(data: Int): Either<NonEmptyList<String>, Int> =
        if (data > 0) Either.right(data)
        else Either.left(NonEmptyList.of("$data is negative"))

}
