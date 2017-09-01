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
 * They will not be generated if explicitly defined
 */

// TODO("create a class Director with name: String, bio: String and filmography: List<String>")
// Filmography should have a default value of empty list
data class SpecialDirector(val name: String, val bio: String, val filmography: List<String> = emptyList())


/**
 * Companion objects
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
    // TODO("change the value of the actualPath variable to capitals")
    var actualPath: String = fileName.toUpperCase()

    // TODO("insert init block here; don't forget to call the callback")
    init {
        callback("Contents of $actualPath")
    }

    // TODO("insert companion object here to create a new instance of the class")
    companion object {
        fun build(fileName: String, callback: (String) -> Unit): FileFilmLoader =
            FileFilmLoader(fileName, callback)
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

object Comedy : Genre("Comedy")

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
    Pair("world", "the earth, together with all of its countries and peoples")
)


/**
 * Collections, Structures Map, Pair
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
    //TODO("filter films of this director")
    return films
        .filter { it.director == director }
}

/**
 * filterFilmsUsingFilter should return films directed by the given director (using given filter)
 */
fun filterFilmsUsingFilter(films: List<Film>, withCustomFilter: (Film) -> Boolean): List<Film> {
    //TODO("filter using high order function (function as parameter)")
    return films
        .filter(withCustomFilter)
}

/**
 * sumPricesWithFolding should use List#foldLeft to return the given films prices sum
 * Do you know the difference between 'reduce' and 'fold'?
 */
fun sumPricesWithFolding(films: List<Film>): Double {
    // TODO("map the film to its price, then 'fold' the sum")
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
    // TODO("use fold to delete duplicate films")
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
    //TODO("map the film to its price, then apply the discount when applies")
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
    //TODO("map each type according to its value")
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
