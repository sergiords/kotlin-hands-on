package fr.xebia.xke

/**
 * Data class is a class to do nothing but hold data
 *
 * The same concepts exist in other languages: Lombock in Java, Case Classes in Scala, etc
 *
 * The compiler automatically derives:
 * - equals()/hashCode()
 * - toString()
 * - componentN() functions corresponding to the properties in their order of declaration
 * - copy()
 *
 * These methods will not be generated if explicitly defined
 */
// TODO transform this class to a data class
data class SpecialDirector(val name: String, val bio: String)


/**
 * Objects & Companion objects
 *
 * The 'static' keyword is not present in kotlin but there are two ways to define static-like methods: objects and companion objects
 *
 * Objects:
 *  object Singleton { ... } // defines a class and its associated singleton all-together
 *
 * Companion objects:
 *  class File {
 *      companion object {
 *          fun load(path: String) { ... } // called with File.load(...)
 *      }
 * }
 */
object Ops {
    var name = "" // Ops.name = "Bob"
}

// TODO return true if an Ops has no name (name is empty)
fun anOpsHasNoName(): Boolean = Ops.name.isEmpty()


class FileFilmLoader private constructor(fileName: String) {

    // TODO change the value of the actualPath variable to upper case
    var actualPath: String = fileName.toUpperCase()

    // TODO insert companion object here and implement build method as a factory for FileFilmLoader (FileFilmLoader.build(...))
    companion object {
        fun build(fileName: String): FileFilmLoader = FileFilmLoader(fileName)
    }

}


/**
 * Sealed class
 *
 * Sealed class are used for representing restricted class hierarchies
 *
 * A sealed class can have subclasses, but all of them must be declared in the same file as the sealed class itself
 */
// TODO seal this open class to prevent subclasses defined outside of this file
sealed class Genre(val description: String) {
    override fun toString() = description
}

object Action : Genre("Action")

object Drama : Genre("Drama")

object Horror : Genre("Horror")

object Thriller : Genre("Thriller")

object Crime : Genre("Crime")

object War : Genre("War")

object Romance : Genre("Romance")

object Mistery : Genre("Mistery")

object SciFi : Genre("Sci-Fi")

// TODO seal this open class to prevent subclasses defined outside of this file
sealed class Director(val name: String)

object Kurosawa : Director("Kurosawa")

object Hitchcock : Director("Hitchcock")

object Spielberg : Director("Unknown")

// TODO use a when expression to map genres to one movie of the genre, notice that else branch is optional once Genre is sealed
fun directorYearOfBirth(director: Director): Int = when (director) {
    Kurosawa -> 1910
    Hitchcock -> 1899
    Spielberg -> 1946
    // else -> -1 // optional once Director class is sealed
}


/**
 * Getters & setters
 *
 * The full syntax for declaring a property is:
 *
 *   var <propertyName>[: <PropertyType>] [= <property_initializer>]
 *     [get() { return ... }]
 *     [set(value) { ... }]
 */
class FilmCollection(private val films: List<Film>) {

    private var startingYear = 1990

    // TODO define a getter for this property returning true if there are films released between startingYear and 1989
    val has80sFilms
        get() = this.films.any { it.releaseYear in startingYear..1989 }

    // TODO define a setter for this property changing private startingYear variable
    var periodStartYear = startingYear
        set(value) {
            startingYear = value
        }
}


/**
 * Getters & Setters: Backing field
 *
 * Another example of custom code in both getters and setters
 *
 * You can refer to property's current value in the following way
 *
 *  var dist: Int = 0
 *      get() = field + 1
 *
 */
class StringMagicBox {

    var reversed: String = "Some value"

        // TODO implement getter to return backing field's value in reversed order
        get() = field.reversed()

        // TODO implement setter to assign value to it's backing field
        set(value) {
            field = value
        }

}


/**
 * Generics: declaration-site variance: Co-Variance 'out'
 */
sealed class Text

class SMS : Text()
class Mail : Text()

interface Sender<out T> {

    fun send(): T

    // fun receive(t: T) // Does not compile ! T can not be passed as a parameter
}

// TODO return given parameter and notice how we can assign a Sender<Mail> to a Sender<Text>
fun textSender(mailSender: Sender<Mail>): Sender<Text> = mailSender


/**
 * Generics: declaration-site variance: Contra-Variance 'in'
 */
interface Receiver<in T> {

    // fun send(): T // Does not compile ! T can not be returned

    fun receive(t: T)
}

// TODO return given parameter and notice how we can assign a Receiver<Text> to a Receiver<SMS>
fun textReceiver(textReceiver: Receiver<Text>): Receiver<SMS> = textReceiver


/**
 * Generics: use-site variance
 *
 * Sometimes type parameter variance can not be expressed at type level
 * It is possible to express it at function parameter level
 */
fun useSiteVariance(arrayIn: Array<in Int>, arrayOut: Array<out Int>) {

    // TODO copy arrayOut[3] element to arrayIn[5] element
    arrayIn[5] = arrayOut[3]

    // TODO notice how arrayOut elements can not be assigned and arrayIn can not be read as Int
    // val x: Int = arrayIn[0] // Does not compile
    // arrayOut[0] = x         // Does not compile
}


/**
 * Type Aliases allows programmer to use a more clearly type than the existing name type
 *
 * typealias HashPassword = String
 *
 * fun check(hash: HashPassword) { ... }
 * check("Plain String") // works just fine
 *
 * We can't use type aliases to constrain parameters: check function accepts both HashPassword and String parameters
 */
// TODO define a type alias Dictionary for Map<String, String> and replace dict variable Type to use it
typealias Dictionary = Map<String, String>

val dict: Dictionary = mapOf(
    Pair("hello", "used as a greeting or to begin a telephone conversation"),
    Pair("world", "the earth, together with all of its countries and peoples")
)


/**
 * Collections, Structures Map, Pair
 */
data class Film(val name: String,
                val releaseYear: Int,
                val director: Director,
                val type: List<Genre>,
                val price: Int = 0)

/**
 * Collections in Kotlin have classic functional programming functions
 *
 * In the following exercises we are going to explore some of these functions
 */

/**
 * Using filters with lambdas
 */
// TODO return films directed by the given director
fun filmsMadeBy(director: Director, films: List<Film>): List<Film> =
    films
        .filter { it.director == director }

/**
 * Using high-order function as parameter
 *
 * One can specify a function as a paramater with the following syntax:
 *  fun <T> filter(predicate: (T) -> Boolean) { ... }
 *
 * In Kotlin there is no need to declare a specific class (like Predicate, Supplier, Consumer, ... classes in Java)
 */
// TODO return films matching the given filter
fun filmsMatchingFilter(films: List<Film>, filter: (Film) -> Boolean): List<Film> =
    films
        .filter(filter)

/**
 * Using map & fold functions
 *
 * Do you know the difference between 'reduce' and 'fold'?
 */
// TODO map each film to its price then 'fold' the sum of prices
fun sumPricesWithFolding(films: List<Film>): Int =
    films
        .map { it.price }
        .fold(0, { total, next -> total + next })

/**
 * Initializing collections with Kotlin
 *
 * Kotlin provides functions to easily initialize lists, maps, sets, ...
 *
 * val list = listOf(1, 2, 3, 4)
 * val map  = mapOf(1 to "one", 2 to "two")
 * val set  = setOf(1, 2, 2, 2)
 */
// TODO initialize an immutable list of two films from Spielberg, specify at least one genre for each
val films: List<Film> = listOf(
    Film("Jurassic Park", 1993, Spielberg, listOf(SciFi)),
    Film("Minority Report", 2002, Spielberg, listOf(SciFi))
)

// TODO initialize a mutable map containing at least 2 keys (years) with an associated list of films (at least 1)
val filmsByYear: MutableMap<Int, List<Film>> = mutableMapOf(
    1993 to listOf(films[0]),
    2002 to listOf(films[1])
)


/**
 * Sequence
 *
 * Sequence is similar to Java 8 streams: lazily evaluated and potentially infinite
 */
val seq = sequenceOf(1, 2, 3, 4, 5)

// TODO map seq elements using given map function, return true if any mapped element is >= 4, notice that only first 2 elements are mapped (check tests)!
fun filterSeq(map: (Int) -> Int): Boolean =
    seq
        .map(map)
        .any { it >= 4 }


/**
 * Pairs
 *
 * Pair can hold two value without requiring a specific class

 * Triple also exist but use it wisely...
 */
val chinesePhilosophy = Pair("Yin", "Yang")
val starWarsPhilosophy = Pair("Dark side", "Bright side")

// TODO return a pair of pairs containing dark and bright sides of each philosophy together
fun mapPhilosophies(): Pair<Pair<String, String>, Pair<String, String>> = Pair(
    Pair(chinesePhilosophy.first, starWarsPhilosophy.first),
    Pair(chinesePhilosophy.second, starWarsPhilosophy.second)
)


/**
 * Delegates allow customizing how a value is initialized
 *
 * One common delegate is lazy() function which computes a value only when it used
 *
 * val myValue: String by lazy { "Computed only when read" }
 */
// TODO initialize lazyValue using a lazy delegator and increment lazyInc by one in initialization block
var lazyInc = 0

val lazyValue: String by lazy {
    lazyInc++
    "hey"
}
