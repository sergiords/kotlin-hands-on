package fr.xebia.xke

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.LocalDate
import java.time.Month

/**
 * Mocks and Tests: classes
 *
 * Classes in kotlin are final by default
 *
 * To test Service using a mock for Repository either:
 *  - open class and method to be mocked to allow mockito mocking it
 *  - enable Mockito to mock final classes: http://hadihariri.com/2016/10/04/Mocking-Kotlin-With-Mockito/
 *    add 'mock-maker-inline' line in test/resources/mockito-extension/org.mockito.plugins.MockMaker
 */
// TODO fix tests by allowing repository to be properly mocked
class Repository {

    fun data(): String = "Some Data"

}

class Service(private val repository: Repository) {

    fun transformedData(): String = repository.data() + " from Service"

}

/**
 * Mocks and Tests: matching and capturing arguments
 *
 * Matching or capturing mock arguments can also be tricky since Kotlin types are not null by default
 * And mockito when matching or capturing mock parameters returns null values...
 *
 * To work around this issue modify following functions in tests: anyNotNull() and captureNotNull()
 */
class Greeter {

    // TODO modify anyNotNull() and captureNotNull() in tests to allow matching and capturing mock parameters
    fun welcome(name: String): String = "Hello $name"

}


/**
 * Kotlin ♡ JSON
 *
 * example:
 *  val user: User = "{...}".jsonDeserialize<User>()
 *  val json: String = user.jsonSerialize()
 */
data class User(val name: String, val age: Int)

// TODO initialize an object mapper with KotlinModule or using jacksonObjectMapper function
val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

// TODO complete this function extension to serialize any object to json (use previous mapper)
fun <T> T.jsonSerialize(): String = mapper.writeValueAsString(this)

// TODO complete this function extension to deserialize a String to an object of the given type T (use previous mapper)
inline fun <reified T : Any> String.jsonDeserialize(): T = mapper.readValue(this)


/**
 * Java Interoperability
 *
 * Kotlin/Java interoperability features were already illustrated in previous exercises but here are a few more
 */

// Some Java types are 'merely' mapped to kotlin types
// TODO return java Class corresponding to the given T type
inline fun <reified T> javaType(): Class<T> = T::class.java

// Java getters/setters: this.year refers to getYear(), this.dayOfMonth refers to getDayOfMonth(), ...
// TODO return a local date in same year and day but in january (notice how getters are translated in Kotlin)
fun LocalDate.toJanuary(): LocalDate = LocalDate.of(this.year, Month.JANUARY, this.dayOfMonth)


/**
 * Kotlin interoperability
 *
 * Kotlin types can also be accessed from Java
 * Build and import this project in a Java project of your own to test Java/Kotlin interoperability
 * check following links for more:
 *  - https://kotlinlang.org/docs/reference/java-interop.html
 *  - https://kotlinlang.org/docs/reference/java-to-kotlin-interop.html
 */

/**
 *
 * That's all folks!
 *
 */
