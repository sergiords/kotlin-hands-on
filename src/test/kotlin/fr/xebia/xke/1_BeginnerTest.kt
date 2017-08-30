package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PropertiesTest : StringSpec({

    val expectedHost = "xebia.fr"
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

class LambdasTest : StringSpec({

    mapOf(
        0 to true,
        1 to false,
        2 to true,
        3 to false,
        42 to true,
        51 to false,
        -1 to false,
        -2 to true
    ).forEach {

        "${::pair.name}(${it.key}) should return ${it.value}" {
            pair(it.key) shouldBe it.value
        }

    }

    mapOf(
        0 to 2,
        2 to 0,
        4 to 2,
        3 to 1,
        10 to 5
    ).forEach {

        val expected = it.key * it.value
        "${::product.name}(${it.key}, ${it.value}) should return $expected" {
            product(it.key, it.value) shouldBe expected
        }

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


    mapOf(
        TotalPrice(10.0) to 10.0,
        PromotionalPrice(12.0, 5.0) to 7.0,
        null to 0.0
    ).forEach {
        "${::computePrice.name} Prize of (${it.key}) should be ${it.value}" {
            computePrice(it.key) shouldBe it.value
        }
    }

    mapOf(
        "https://www.xebia.fr?par=1234" to 1234,
        "https://www.xebia.fr" to null,
        "https://www.xebia.fr?par=hello" to null
    ).forEach {
        "${::computeUrlValue.name} Parameter of url (${it.key}) should be ${it.value}" {
            computeUrlValue(it.key) shouldBe it.value
        }
    }

    mapOf(
        "REFERENCE 1" to "REFERENCE 1",
        1234 to "1234",
        1.0 to null
    ).forEach {
        "${::computeProductReference.name} reference (${it.key}) should be ${it.value}" {
            computeProductReference(it.key) shouldBe it.value
        }
    }

    mapOf(
        1 to listOf(1),
        0 to emptyList(),
        5 to listOf(1, 2, 3, 4, 5)
    ).forEach {
        "${::generateIntegerList.name} list (${it.key}) should be ${it.value}" {
            generateIntegerList(it.key) shouldBe it.value
        }
    }

    mapOf(
        1 to 0,
        0 to 0,
        3 to 3,
        6 to 9,
        10 to 18,
        9 to 18
    ).forEach {
        "${::sumSequenceNumbers.name} sum of (${it.key}) values should be ${it.value}" {
            sumSequenceNumbers(it.key) shouldBe it.value
        }
    }

    mapOf(
        listOf(Product("aaa", TotalPrice(10.0))) to 10.0,
        listOf(Product("aaa", TotalPrice(10.0)), Product("bbb", PromotionalPrice(15.0, 5.0))) to 20.0,
        listOf(Product("aaa", TotalPrice(10.0)), Product("bbb", PromotionalPrice(15.0, 5.0)), Product("aaa", PromotionalPrice(20.0, 2.0)))
            to 38.0,
        listOf(Product("aaa", PromotionalPrice(10.0, 3.0)), Product("bbb", PromotionalPrice(15.0, 5.0)), Product("aaa", PromotionalPrice(20.0, 2.0)))
            to 28.0
    ).forEach {
        "${::computeTotalPrice.name} price of (${it.key}) should be ${it.value}" {
            computeTotalPrice(it.key, 3) shouldBe it.value
        }
    }

    mapOf(
        listOf(5, 4, 3, 2, 1) to 5,
        listOf(8, 7, 6) to 3,
        listOf(1, 1, 1, 1) to 4

    ).forEach {
        "${::computeControlNumberSiren.name} control number of (${it.key}) should be ${it.value}" {
            computeControlNumberSiren(it.key) shouldBe it.value
        }
    }


})
