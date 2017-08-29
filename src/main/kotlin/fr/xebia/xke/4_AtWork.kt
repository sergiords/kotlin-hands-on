package fr.xebia.xke

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Mocks and Tests: classes
 *
 * Classes in kotlin are final by default
 *
 * To test Service using a mock for Repository either:
 *  - open class to allow mockito to mock it
 *  - enable Mockito to mock final classes: http://hadihariri.com/2016/10/04/Mocking-Kotlin-With-Mockito/
 *    add 'mock-maker-inline' line in test/resources/mockito-extension/org.mockito.plugins.MockMaker
 */
class Repository {

    fun data(): String = "Some Data"

}

class Service(private val repository: Repository) {

    fun transformedData(): String = repository.data() + " from Service"

}

/**
 * Mocks and Tests: matching and capturing arguments
 *
 * Specifying mock behaviour for given arguments can also be tricky since Kotlin types are not null by default
 *
 * And mockito when specifying or capturing mocking behaviour returns null values...
 *
 * To work around this issue modify following functions in tests: anyNotNull() and captureNotNull()
 */
class Greeter {

    fun welcome(name: String): String = "Hello $name"

}


/**
 * Kotlin ♡ JSON
 */
data class User(val name: String, val age: Int)

// TODO: initialize an object mapper with KotlinModule or using jacksonObjectMapper function
val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

fun <T> T.jsonSerialize(): String = mapper.writeValueAsString(this)

inline fun <reified T : Any> String.jsonDeserialize(): T = mapper.readValue(this)
