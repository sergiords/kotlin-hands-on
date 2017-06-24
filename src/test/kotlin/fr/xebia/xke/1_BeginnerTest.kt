package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PropertiesTest : StringSpec({

    val expectedHost = "Kotlin is fun"
    "${::host.name} should be initialized to $expectedHost" {
        host shouldBe expectedHost
    }

    val expectedPort = 443
    "${::port.name} should be initialized to $expectedPort" {
        port shouldBe expectedPort
    }

    val expectedURL = "https://blog.$expectedHost:$expectedPort"
    "${::url.name} should be initialized to $expectedURL" {
        url shouldBe expectedURL
    }

})

class FunctionsTest : StringSpec({

    mapOf(
        "http://example.com" to false,
        "https://example.com" to true
    ).forEach {
        "${it.key} should be marked as ${if (it.value) "secure" else "insecure"}" {
            isSecured(it.key) shouldBe it.value
        }
    }

    mapOf(-1 to 1, 1 to 1, 2 to 2, 3 to 6, 4 to 24, 7 to 5040).forEach {
        "${::factorial.name}(${it.key}) should be ${it.value}" {
            factorial(it.key) shouldBe it.value
        }
    }

    mapOf(
        1 to "1 second(s)", 2 to "2 second(s)",
        60 to "1 minute(s)", 120 to "2 minute(s)",
        3600 to "1 hour(s)", 7200 to "2 hour(s)",
        86400 to "1 day(s)", 172800 to "2 day(s)"
    ).forEach {
        "${::remainingTime.name}(${it.key}) should be ${it.value}" {
            remainingTime(it.key) shouldBe it.value
        }
    }

})
