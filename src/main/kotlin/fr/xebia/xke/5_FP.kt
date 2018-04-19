package fr.xebia.xke

import arrow.core.*

import arrow.core.Either
import arrow.core.flatMap
import arrow.data.*

/**
 * This class validates user data in two ways:
 * - Applicative for all fields (validated all fields)
 * - Monadic within each field (stops on the first error per field)
 *
 *
 * The following functions might help you
 * - Option.fromNullable("something")
 * - Option(maybeRight).toEither { "Left value" }
 * - Try { data.toInt() }.toEither().mapLeft { NonEmptyList.of("$name must be an integer") }
 * - Either.cond(true, { 42 }, { "Error" })
 * - Validated.fromEither(...)
 * - Validated.applicative(NonEmptyList.semigroup<String>())
 *     .map2(validated1, validated2, { v: Tuple<Int, Int> -> v.a + v.b })
 *     .fix()
 * - Validated.applicative(NonEmptyList.semigroup<String>())
 *    .tupled(validated1, validated2, ..., validatedN)
 *    .fix()
 *    .map{ v: TupleN<Int, Int> -> v.a + v.b }
 * - NonEmptyList.of("a string", "another string")
 *
 */
class UserValidation {

    // TODO find a way to aggregate in an applicative way the 'readName' and 'readAge' functions
    fun validateUser(params: Map<String, String>): Validated<NonEmptyList<String>, User>  {
        TODO()
    }

    // TODO read the field 'name' and validate that is non blank
    fun readName(params: Map<String, String>): Either<NonEmptyList<String>, String> =
        TODO()

    // TODO read the field 'age' and validate that is non blank, valid int, and non negative
    fun readAge(params: Map<String, String>): Either<NonEmptyList<String>, Int> =
        TODO()

    // TODO get a value from the map or return an error
    fun getValue(name: String, data: Map<String, String>): Either<NonEmptyList<String>, String> =
        TODO()


}
