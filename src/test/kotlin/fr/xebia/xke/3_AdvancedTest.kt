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
