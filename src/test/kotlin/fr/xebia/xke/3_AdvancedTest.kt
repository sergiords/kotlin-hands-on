package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

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

class ExtensionPropertyTest : StringSpec({

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

class FunctionTypeReceiverTest : StringSpec({

    val expectedBuild = Build("myProject", arrayListOf("libA", "libB"))
    "${::myBuild.name} should be equal to $expectedBuild" {
        myBuild shouldBe expectedBuild
    }

})

class DestructuringDeclarationsTest : StringSpec({
    val prettyTime = getPrettyTime(MyTime(10, 10))
    val expectedPrettyTime = "10:10"
    "$prettyTime should be equal to $expectedPrettyTime" {
        prettyTime shouldBe expectedPrettyTime
    }
})
