package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate

class OperatorOverloadingTest : StringSpec({

    val expectedV3 = Vector(v1.dx + v2.dx, v1.dy + v2.dy)
    "${::v3.name} should be equal to $expectedV3 (${::v1.name} + ${::v2.name})" {
        v3 shouldBe expectedV3
    }

    val expectedV4 = Vector(v1.dx - v2.dx, v1.dy - v2.dy)
    "${::v4.name} should be equal to $expectedV4 (${::v1.name} - ${::v2.name})" {
        v4 shouldBe expectedV4
    }

})

class ExtensionFunctionTest : StringSpec({

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

