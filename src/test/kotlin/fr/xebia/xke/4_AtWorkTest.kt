@file:Suppress("UNREACHABLE_CODE", "UnusedImport", "unused")

package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito
import java.time.LocalDate

fun <T> anyNotNull(): T {
    TODO("invoke mockito any() method")
    TODO("mislead Kotlin by returning a null value but casted to a not nullable type T")
}

fun <T> ArgumentCaptor<T>.captureNotNull(): T {
    TODO("invoke mockito capture() method")
    TODO("mislead Kotlin by returning a null value but casted to a not nullable type T")
}

class AtWork_MocksAndTestsTest : StringSpec({

    val expected = "Test Data from Service"
    "${Service::transformedData.name}() should return '$expected'" {

        val repositoryMock = mock<Repository>()
        val service = Service(repositoryMock)

        given(repositoryMock.data()).willReturn("Test Data")
        service.transformedData() shouldBe expected
    }

    "Arguments should be allowed in mocks using any()" {
        val greeterMock = mock<Greeter>()
        given(greeterMock.welcome(anyNotNull())).willAnswer({ "Hello ${it.arguments[0]} from Mock" })

        val result = greeterMock.welcome("Test")

        result shouldBe "Hello Test from Mock"
        verify(greeterMock).welcome(anyNotNull())
    }

    "Arguments should be captured in mocks using capture()" {
        val greeterMock = mock<Greeter>()
        given(greeterMock.welcome(anyNotNull())).willReturn("Result")

        greeterMock.welcome("Test")

        val captor = captor<String>()
        verify(greeterMock).welcome(captor.captureNotNull())
        captor.value shouldBe "Test"
    }

})

class AtWork_KotlinLovesJsonTest : StringSpec({

    mapOf(
        User("Bob", 30) to """{"name":"Bob","age":30}""",
        User("Alice", 20) to """{"name":"Alice","age":20}"""
    ).forEach {

        "${it.key} should be serialized to ${it.value}" {
            it.key.jsonSerialize() shouldBe it.value
        }

        "${it.value} should be deserialized to ${it.key}" {
            it.value.jsonDeserialize<User>() shouldBe it.key
        }

    }

})

class AtWork_JavaInteroperabilityTest : StringSpec({

    "javaType<String>() should return original Java type" {
        javaType<String>() shouldBe java.lang.String::class.java
    }

    "javaType<Exception>() should return original Java type" {
        javaType<Exception>() shouldBe java.lang.Exception::class.java
    }

    "javaType<Any>() should return original Java type" {
        javaType<Any>() shouldBe java.lang.Object::class.java
    }

    mapOf(
        LocalDate.of(2000, 10, 1) to LocalDate.of(2000, 1, 1),
        LocalDate.of(2010, 10, 31) to LocalDate.of(2010, 1, 31)
    ).forEach {

        "${LocalDate::toJanuary.name}() should change ${it.key} to ${it.value}" {
            it.key.toJanuary() shouldBe it.value
        }

    }

})

/**
 * Some sugar to create ArgumentCaptor<T>
 */
inline fun <reified T> captor(): ArgumentCaptor<T> = ArgumentCaptor.forClass(T::class.java)
