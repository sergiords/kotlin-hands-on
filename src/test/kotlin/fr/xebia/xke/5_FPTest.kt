package fr.xebia.xke

import arrow.data.NonEmptyList
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

import arrow.syntax.validated.*

class FP_MockTest : StringSpec({

    mapOf(
        mapOf("name" to "Terah", "age" to "34") to User("Terah", 34).valid<NonEmptyList<String>, User>(),
        mapOf("age" to "34") to NonEmptyList.of("name is not present").invalid(),
        mapOf("name" to "", "age" to "34") to NonEmptyList.of("name is blank").invalid(),
        mapOf("name" to "Terah") to NonEmptyList.of("age is not present").invalid(),
        mapOf("name" to "Terah", "age" to "") to NonEmptyList.of("age is blank").invalid(),
        mapOf("name" to "Terah", "age" to "???") to NonEmptyList.of("age must be an integer").invalid(),
        mapOf("name" to "Terah", "age" to "-1") to NonEmptyList.of("-1 is negative").invalid(),
        emptyMap<String, String>() to NonEmptyList.of("name is not present", "age is not present").invalid()
    ).forEach {
        "${it.key} should be ${it.value}" {
            UserValidation().validateUser(it.key). shouldBe (it.value)
        }
    }

})
