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
    Mockito.any<T>() // still use mockito any() behaviour
    @Suppress("UNCHECKED_CAST")
    return null as T // return a not-null-but-still-null instance
}

fun <T> ArgumentCaptor<T>.captureNotNull(): T {
    this.capture() // still use mockito capture() behaviour
    @Suppress("UNCHECKED_CAST")
    return null as T // return a not-null-but-still-null instance
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

    mapOf(
        javaType<String>() to java.lang.String::class.java,
        javaType<Exception>() to java.lang.Exception::class.java,
        javaType<Any>() to java.lang.Object::class.java
    ).forEach {

        "javaType<${it.value.simpleName}>() should return original Java type ${it.value}" {
            it.key shouldBe it.value
        }

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
