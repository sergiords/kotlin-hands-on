package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class DataClassTest : StringSpec({

    val john = Director(
        name = "John Carpenter",
        bio = "was born in Carthage, New York")

    "it should provide a 'toString'" {
        john.toString() shouldBe "Director(name=John Carpenter, bio=was born in Carthage, New York, filmography=[])"
    }

    "it should provide a 'copy'" {
        val updatedJohn = john.copy(name = "Mel Brooks")
        updatedJohn.bio shouldBe john.bio
    }

    "it should be allow deconstructions " {
        val updatedJohn =
            john.copy(filmography =
            mutableListOf(
                "Halloween",
                "Escape from LA",
                "Big Trouble in Little China"))
        filmographyContaining(updatedJohn, "Trouble") shouldBe
            mutableListOf("John Carpenter - Big Trouble in Little China")
    }

    "it should be able to use companion objects" {
        val csvReader = CsvFilmLoader.build("file.csv") { it shouldBe "Contents of FILE.CSV" }
        csvReader.actualPath shouldBe "FILE.CSV"

        val xmlReader = CsvFilmLoader.build("file.xml") { it shouldBe "Contents of FILE.XML" }
        xmlReader.actualPath shouldBe "FILE.XML"
    }

})

class GenericsTest : StringSpec({

    "partitionWordsAndLines should" {
        val (words, lines) = listOf("a", "a b", "c", "d e").partitionWordsAndLines()
        listOf("a", "c") shouldBe words
        listOf("a b", "d e") shouldBe lines
    }

    "testPartitionLettersAndOtherSymbols should" {
        val (letters, other) = setOf('a', '%', 'r', '}').partitionLettersAndOtherSymbols()
        setOf('a', 'r') shouldBe letters
        setOf('%', '}') shouldBe other
    }

})
