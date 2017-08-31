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

class TypeChecksSmartCastsTest : StringSpec({

    val stdPrice = StandardPrice(10)
    "${::computePrice.name}(StandardPrice(${stdPrice.value})) should be ${stdPrice.value}" {
        computePrice(stdPrice) shouldBe stdPrice.value
    }

    val promoPrice = PromotionalPrice(12, 5)
    val expectedPromoPrice = promoPrice.value - promoPrice.discount
    "${::computePrice.name}(PromotionalPrice(${promoPrice.value}, ${promoPrice.discount})) should be $expectedPromoPrice" {
        computePrice(promoPrice) shouldBe expectedPromoPrice
    }

    val unknownPrice = object : Price {}
    "${::computePrice.name}(UnknownPriceType) should be 0" {
        computePrice(unknownPrice) shouldBe 0
    }

})

class NullReferencesTest : StringSpec({

    mapOf(
        "https://www.xebia.fr?par=1234" to 1234,
        "https://www.xebia.fr?par=1234&par2=4321" to 4321,
        "https://www.xebia.fr" to null,
        "https://www.xebia.fr?par=hello" to null,
        "https://www.xebia.fr?par=hello&par2=toto" to null
    ).forEach {
        "${::convertURLParam.name}(${it.key}) should be ${it.value}" {
            convertURLParam(it.key) shouldBe it.value
        }
    }

    mapOf(
        StandardPrice(10) to 10L,
        null to 0L
    ).forEach {
        "${::convertPriceToLong.name}(Price(${it.key?.value}) should be ${it.value}" {
            convertPriceToLong(it.key) shouldBe it.value
        }
    }

})

class RangesAndLoopsTest : StringSpec({

    mapOf(
        0 to emptyList(),
        1 to listOf(1),
        5 to listOf(1, 2, 3, 4, 5)
    ).forEach {
        "${::generateIntegerList.name}(${it.key}) should generate a list of ${it.value.size} element(s)" {
            generateIntegerList(it.key) shouldBe it.value
        }
    }

    mapOf(
        0 to 0,
        1 to 0,
        3 to 3,
        6 to 9,
        9 to 18,
        10 to 18
    ).forEach {
        "${::sumSequenceNumbers.name}(${it.key}) should return ${it.value}" {
            sumSequenceNumbers(it.key) shouldBe it.value
        }
    }

    mapOf(
        listOf(StandardPrice(10)) to 10,
        listOf(StandardPrice(10), PromotionalPrice(15, 5)) to 20,
        listOf(StandardPrice(10), PromotionalPrice(15, 5), PromotionalPrice(20, 2)) to 33,
        listOf(StandardPrice(10), StandardPrice(15), PromotionalPrice(20, 3)) to 42
    ).forEach {
        val stdPrices = it.key.count{ it is StandardPrice }
        val promoPrices = it.key.count{ it is StandardPrice }
        "${::computeTotalPrice.name}() of $stdPrices standard price(s) & $promoPrices promotional price(s) should be ${it.value}" {
            computeTotalPrice(it.key) shouldBe it.value
        }
    }

})
