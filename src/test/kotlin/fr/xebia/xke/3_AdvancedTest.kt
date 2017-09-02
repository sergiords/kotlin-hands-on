package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.time.LocalDate
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

class Advanced_OperatorOverloadingTest : StringSpec({

    val expectedV3 = Vector(v1.dx + v2.dx, v1.dy + v2.dy)
    "${::v3.name} should be equal to $expectedV3 (${::v1.name} + ${::v2.name})" {
        v3 shouldBe expectedV3
    }

    val expectedV4 = Vector(v1.dx - v2.dx, v1.dy - v2.dy)
    "${::v4.name} should be equal to $expectedV4 (${::v1.name} - ${::v2.name})" {
        v4 shouldBe expectedV4
    }

})

class Advanced_CoroutineTest : StringSpec({

    "${::fibonacciSeq.name} should generate a lazily initialized Fibonacci sequence" {
        val list = fibonacciSeq.take(5).toList()
        list shouldEqual listOf(1, 1, 2, 3, 5)
    }

    "${::fiveFirstFibonacci.name} should be initialized to take 5 elements only" {
        fiveFirstFibonacci().toList() shouldEqual listOf(1, 1, 2, 3, 5)
    }

    "giveTreatment() to patient should return aspirin after a delay of one second" {
        val start = System.currentTimeMillis()
        val res = runBlocking {
            val promise = async(CommonPool) {
                giveTreatment()
            }
            promise.await()
        }
        res shouldEqual "aspirin"
        ((System.currentTimeMillis() - start) >= 1000) shouldBe true
    }

})

class Advanced_ExtensionFunctionTest : StringSpec({

    val expectedArea = squareSize * squareSize
    "${::squareArea.name} should be $expectedArea" {
        squareArea shouldBe expectedArea
    }

    "${::ofCourse.name} should be true" {
        ofCourse shouldBe true
    }

    "${::noWay.name} should be false" {
        noWay shouldBe false
    }

    mapOf(
        "Hello" to true,
        "World" to true,
        100 to false,
        Any() to false
    ).forEach {
        "${it.key::class.simpleName} should ${if (it.value) "" else "not "}be considered as a String" {
            it.key.isString() shouldBe it.value
        }
    }

})

class Advanced_ExtensionPropertyTest : StringSpec({

    val expectedCelsiusValue = 10
    "${::celsiusValue.name} should be $expectedCelsiusValue °C" {
        celsiusValue shouldBe expectedCelsiusValue
    }

    val expectedKelvinValue = 283
    "${::kelvinValue.name} should be $expectedKelvinValue °K" {
        kelvinValue shouldBe expectedKelvinValue
    }

    mapOf(
        Temperature(0) to 273,
        Temperature(100) to 373,
        Temperature(-273) to 0
    ).forEach {
        "${it.key.celsius} °C should equal to ${it.value} °K" {
            it.key.kelvin shouldBe it.value
        }
    }

})

class Advanced_FunctionTypeReceiverTest : StringSpec({

    val expectedBuild = Build("myProject", arrayListOf("libA", "libB"))
    "${::myBuild.name} should be equal to $expectedBuild" {
        myBuild shouldBe expectedBuild
    }

})

class Advanced_InlineFunctionsTest : StringSpec({

    val witness = arrayListOf<String>()
    val logger = Logger.getLogger("TestLogger")
    logger.addHandler(object : Handler() {
        override fun flush() {}
        override fun close() {}
        override fun publish(record: LogRecord) {
            witness.add(record.message)
        }
    })

    val assertLogCall = { witness.size == 1 && witness[0] == "message" }

    mapOf(
        Level.FINE to true,
        Level.WARNING to false,
        Level.SEVERE to false
    ).forEach {
        "${Logger::fineIfEnabled.name}() should ${if (it.value) "" else "not "}log message at ${it.key} level" {
            witness.clear()
            logger.level = it.key
            logger.fineIfEnabled {
                "message"
            }
            assertLogCall() shouldBe it.value
        }
    }

})

class Advanced_InlineFunctionsWithReifiedParameterType : StringSpec({

    mapOf(
        parameterTypeClass<String>() to String::class.java,
        parameterTypeClass<Int>() to Int::class.javaObjectType,
        parameterTypeClass<LocalDate>() to LocalDate::class.java
    ).forEach {
        "parameterTypeClass<${it.value.simpleName}>() should be equal to ${it.value.simpleName}" {
            it.key shouldBe it.value
        }
    }

})

class Advanced_DestructuringDeclarationsTest : StringSpec({
    val times = listOf(
        MyTime(10, 10),
        MyTime(20, 20),
        MyTime(11, 11),
        MyTime(12, 34)
    )
    "${::formatTimes.name}() should format ${MyTime::class.simpleName} instances using 'hour:minute' format" {
        formatTimes(times) shouldBe listOf("10:10", "20:20", "11:11", "12:34")
    }
})
