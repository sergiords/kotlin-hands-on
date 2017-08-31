package fr.xebia.xke

import java.util.*
import kotlin.collections.ArrayList

/**
 * Task 2.1. Data class is a class to do nothing but hold data
 *
 * The same concepts exist in other languages: Lombock in Java, Case Classes in Scala, etc
 *
 * The compiler automatically derives:
 * - equals()/hashCode()
 * - toString()
 * - componentN() functions corresponding to the properties in their order of declaration
 * - copy()
 *
 * They will not be generated if explicitly defined
 */

// TODO("2.1.1. Create a class Director with name: String, bio: String and filmography: List<String>")
// Filmography should have a default value of empty list
data class SpecialDirector(val name: String, val bio: String, val filmography: List<String> = emptyList())

/**
 * Task 2.2. Companion objects
 *
 * The 'static' keyword is not present in kotlin so we need to create our static code inside a companion object
 * construct
 *
 * companion object {
 *   // static functions goes here
 * }
 *
 * The init blocks lets you write code inside your class constructor
 *
 * init {
 *   print("This is the constructor")
 * }
 *
 */
class FileFilmLoader private constructor(fileName: String, callback: (String) -> Unit) {
    // TODO("2.2.1. Change the value of the actualPath variable to capitals")
    var actualPath: String = fileName.toUpperCase()

    // TODO("2.2.2. insert init block here; don't forget to call the callback")
    init {
        callback("Contents of $actualPath")
    }

    // TODO("2.2.3. Insert companion object here to create a new instance of the class")
    companion object {
        fun build(fileName: String, callback: (String) -> Unit): FileFilmLoader =
            FileFilmLoader(fileName, callback)
    }
}

/**
 * Task 2.3. Sealed class
 *
 * Sealed class are used for representing restricted class hierarchies
 *
 * A sealed class can have subclasses, but all of them must be declared in the same file as the sealed class itself
 */
sealed class Genre(val desc: String) {
    override fun toString(): String {
        return desc
    }
}

object Action : Genre("Action")

object Drama : Genre("Drama")

object Horror : Genre("Horror")

object Thriller : Genre("Thriller")

object Crime : Genre("Crime")

object War : Genre("War")

object Romance : Genre("Romance")

object Mistery : Genre("Mistery")

object Comedy : Genre("Comedy")

object SciFi : Genre("Sci-Fi")


sealed class Director(val name: String)

object Kurosawa : Director("Kurosawa")

object Hitchcock : Director("Hitchcock")

object Spielberg : Director("Spielberg")

object Kubrick : Director("Kubrick")

object RandomDirector : Director("does_not_matter")

/**
 * Task 2.4. getters/setters
 *
 * The full syntax for declaring a property is:
 * <code>
 *   var <propertyName>[: <PropertyType>] [= <property_initializer>]
 *     [<getter>]
 *     [<setter>]
 * <code>
 */
class FilmCollection(val collection: List<Film>) {
    private var startingYear = 1990

    // TODO("2.4.1. custom accessor for has80sFilms")
    val has80sFilms: Boolean
        get() = this.collection
            .filter { it.releaseYear in startingYear..1990 }
            .isNotEmpty()

    // TODO("2.4.2. custom accessor/mutator for has80sFilms")
    var periodStartYear: Int = startingYear
        set(value) {
            startingYear = value
        }

}

/**
 * Another example of custom code in both getter and setter
 *
 * You can refer to property's current value in the following way
 * <code>
 *   var dist: Int = 0
 *       get() = field + 1
 * </code>
 */
class StringMagicBox {

    val toLower: String
        // TODO("2.4.3. implement a getter that returns the expected value")
        get() = "minuscule !"

    var reversed: String = ""
        // TODO("2.4.3. use the 'field' keyword to refering the property's current value")
        get() = field.reversed()
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
 * Type Aliases allows programmer to use a more clearly type than existing name type
 *
 * We cannot use type aliases to constrain parameters: a function accepting a HashPassword parameter will accept any String
 * value for that parameter.
 *
 */
/**
 * TODO replace dict declaration to use Type aliases
 */
//abstract class Dict : Map<String, String>

//val dict = hashMapOf(
//    Pair("hello", "used as a greeting or to begin a telephone conversation"),
//    Pair("world", "the earth, together with all of its countries and peoples"))

typealias Dict = Map<String, String>

val dict: Dict = hashMapOf(
    Pair("hello", "used as a greeting or to begin a telephone conversation"),
    Pair("world", "the earth, together with all of its countries and peoples"))

/**
 * Task 2.7. collections & structures map, pair
 */
data class Film(val name: String,
                val releaseYear: Int,
                val director: Director,
                val type: List<Genre>,
                val price: Double = 0.0)

/**
 * GetFilmsMadeBy should return films directed by the given director
 */
fun getFilmsMadeBy(director: Director, films: List<Film>): List<Film> {
    //TODO("2.7.1. Filter films of this director")
    return films
        .filter { it.director == director }
}

/**
 * filterFilmsUsingFilter should return films directed by the given director (using given filter)
 */
fun filterFilmsUsingFilter(films: List<Film>, withCustomFilter: (Film) -> Boolean): List<Film> {
    //TODO("2.7.2. filter using high order function (function as parameter)")
    return films
        .filter(withCustomFilter)
}

/**
 * sumPricesWithFolding should use List#foldLeft to return the given films prices sum
 * Do you know the difference between 'reduce' and 'fold'?
 */
fun sumPricesWithFolding(films: List<Film>): Double {
    // TODO("2.7.3. map the film to its price, then 'fold' the sum")
    return films
        .map { it.price }
        .fold(0.0, { total, next -> total + next })
}

/**
 * Return a list containing distinct films only
 * Note:
 *  - start from an empty list
 *  - add a new element if not already present
 */
fun deleteDuplicates(allFilms: List<Film>): List<Film> {
    // TODO("2.7.4 use fold to delete duplicate films")
    return allFilms
        .fold(mutableListOf()) { filmList, film ->
            if (filmList.contains(film)) {
                filmList
            } else {
                filmList.add(film)
                filmList
            }
        }
}

/**
 * Return a discount for each film according to the following rules:
 * - 35% reduction if price is only multiple of 3
 * - 40% reduction if price is only multiple of 5
 * - 50% reduction if price is both multiple of 5 and 3
 * - 0% reduction otherwise
 * Note:
 *  - You can define a tuple like this: "Pair(x + 2, x * 2)"
 *  - You could use a when to test the different cases:
 *  <code>
 *  when(elem) {
 *    Pair(2, 4) -> "6"
 *    else -> "default value"
 *  }
 *  </code>
 */
fun discounts(films: List<Film>): List<Double> {
    //TODO("2.7.5. map the film to its price, then apply the discount when applies")
    return films
        .map { it.price }
        .map { p ->
            when (Pair(p % 3 == 0.0, p % 5 == 0.0)) {
                Pair(true, true) -> 0.5 * p
                Pair(true, false) -> 0.35 * p
                Pair(false, true) -> 0.4 * p
                else -> p
            }
        }
}

/**
 * Transform each type of the film following the following criteria:
 * - if the type is Action, Horror or Crime return "$type - ${film.name}"
 * - in the type is Romance or Drama return ":( ${film.name}"
 * - if it is Thriller then return "Thriller -> ${film.director.name}"
 * - otherwise, return the film name
 * <code>
 *  when(elem) {
 *    ... ->
 *    else -> "default value"
 *  }
 *  </code>
 */
fun labelizeFilm(film: Film): List<String> {
    //TODO("2.7.6. map each type according to its value")
    val bestFilms = listOf(Action, Horror, Crime)
    val worstFilms = listOf(Romance, Drama)
    return film
        .type
        .map { type ->
            when (type) {
                in bestFilms -> "$type - ${film.name}"
                in worstFilms -> ":( ${film.name}"
                Thriller -> "Thriller -> ${film.director.name}"
                else -> film.name
            }
        }
}

/**
 * Lazy is a function which is computed once and then keep result in memory.
 */
/**
 * TODO replace fun lazyValue by a lazy value to have lazyInc incremented only once.
 */
var lazyInc = 0

//fun lazyValue(): String {
//    lazyInc++
//    return "hey"
//}

val lazyValue: String by lazy {
    lazyInc++
    "hey"
}
