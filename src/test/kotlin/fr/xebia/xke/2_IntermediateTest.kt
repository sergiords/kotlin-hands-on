package fr.xebia.xke

import fr.xebia.xke.MockFilmData.Companion.completeList
import fr.xebia.xke.MockFilmData.Companion.hitchcockFilms
import fr.xebia.xke.MockFilmData.Companion.kurosawaFilms
import fr.xebia.xke.MockFilmData.Companion.ran
import fr.xebia.xke.MockFilmData.Companion.rashomon
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class MockFilmData {
    companion object {
        val ran = Film("Ran", 1985, Kurosawa, listOf(Action, Drama, War), 2.3)
        val rashomon = Film("Rashomon", 1950, Kurosawa, listOf(Crime, Drama), 3.0)
        val psyco = Film("Psycho", 1960, Hitchcock, listOf(Horror, Mistery, Thriller), 2.7)
        val vertigo = Film("Vertigo", 1958, Hitchcock, listOf(Mistery, Romance, Thriller), 5.1)

        val completeList = listOf<Film>(ran, rashomon, psyco, vertigo)
        val hitchcockFilms = listOf<Film>(psyco, vertigo)
        val kurosawaFilms = listOf<Film>(ran, rashomon)
    }
}

class DataClassTest : StringSpec({

    val john = SpecialDirector(
        name = "John Carpenter",
        bio = "was born in Carthage, New York")

    // ---------------------------------------
    // Task 2.1
    // ---------------------------------------
    "TODO 2.1.1 - it should provide 'toString'" {
        john.toString() shouldBe "SpecialDirector(name=John Carpenter, bio=was born in Carthage, New York, filmography=[])"
    }

    "TODO 2.1.1 - it should provide 'copy'" {
        val updatedJohn = john.copy(name = "Mel Brooks")
        updatedJohn.bio shouldBe john.bio
    }

    "TODO 2.1.2 - it should allow deconstructions " {
        val updatedJohn =
            john.copy(filmography =
            mutableListOf(
                "Halloween",
                "Escape from LA",
                "Big Trouble in Little China"))
        filmographyContaining(updatedJohn, "Trouble") shouldBe
            mutableListOf("John Carpenter - Big Trouble in Little China")
    }

    // ---------------------------------------
    // Task 2.2 Companion objects
    // ---------------------------------------
    "TODO 2.2.3 - it should be able to use companion objects" {
        val csvReader = CsvFilmLoader.build("file.csv") { it shouldBe "Contents of FILE.CSV" }
        csvReader.actualPath shouldBe "FILE.CSV"

        val xmlReader = CsvFilmLoader.build("file.xml") { it shouldBe "Contents of FILE.XML" }
        xmlReader.actualPath shouldBe "FILE.XML"
    }

})


// ---------------------------------------
// Task 2.4
// ---------------------------------------
class GettersSettersTest : StringSpec({
    val withMyFilms = listOf(
        Film("Videodrome", 1984, RandomDirector, listOf(Horror), 9.0),
        Film("Donnie Darko", 2002, RandomDirector, listOf(Drama, SciFi), 10.0)
    )

    "TODO 2.4.1 - Lists should be able to split into words and lines" {
        val filmCollection = FilmCollection(withMyFilms)
        filmCollection.has80sFilms shouldBe false
    }

    "TODO 2.4.2 - Lists should be able to split into words and lines" {
        val filmCollection = FilmCollection(withMyFilms)
        filmCollection.periodStartYear = 1980
        filmCollection.has80sFilms shouldBe true
    }
})

// ---------------------------------------
// Task 2.5
// ---------------------------------------
class GenericsTest : StringSpec({

    "TODO 2.5.1 - Lists should be able to split into words and lines" {
        val (words, lines) = listOf("a", "a b", "c", "d e").splitWordsAndLines()
        listOf("a", "c") shouldBe words
        listOf("a b", "d e") shouldBe lines
    }

    "TODO 2.5.1 - Lists should be able to split into letters and other symbols" {
        val (letters, other) = setOf('a', '%', 'r', '}').splitLettersAndOthers()
        setOf('a', 'r') shouldBe letters
        setOf('%', '}') shouldBe other
    }

})

// ---------------------------------------
// Task 2.7 Collections
// ---------------------------------------
class DataClassWithCollectionsTest : StringSpec({

    "TODO 2.7.1 - it should use List#filter" {
        getFilmsMadeBy(Hitchcock, completeList) shouldBe hitchcockFilms
    }

    "TODO 2.7.2 - it should use films List#filter and high order functions" {
        filterFilmsUsingFilter(completeList, { it.director == Hitchcock }) shouldBe hitchcockFilms
        filterFilmsUsingFilter(completeList, { it.director == Kurosawa }) shouldBe kurosawaFilms
    }

    "TODO 2.7.3 - it should use folding to sum film prices" {
        sumPricesWithFolding(hitchcockFilms) shouldBe 7.8
        sumPricesWithFolding(kurosawaFilms) shouldBe 5.3
    }

    "TODO 2.7.4 - it should delete consecutive duplicates" {
        deleteDuplicates(listOf<Film>()) shouldBe listOf<Film>()
        deleteDuplicates(listOf(ran)) shouldBe listOf(ran)
        deleteDuplicates(listOf(ran, rashomon)) shouldBe listOf(ran, rashomon)
        deleteDuplicates(listOf(ran, rashomon, rashomon)) shouldBe listOf(ran, rashomon)
        deleteDuplicates(listOf(ran, ran, rashomon, rashomon)) shouldBe listOf(ran, rashomon)
    }

    "TODO 2.7.5 - it should use pattern matching" {
        val film1 = Film("Videodrome", 1984, RandomDirector, listOf(Horror), 9.0)
        val film2 = Film("Donnie Darko", 2002, RandomDirector, listOf(Drama, SciFi), 10.0)
        val film3 = Film("The Adventures of Baron Munchausen", 1989, RandomDirector, listOf(Comedy), 15.0)
        val film4 = Film("Brazil", 1985, RandomDirector, listOf(SciFi), 2.0)
        val discountFilms = listOf(film1, film2, film3, film4)
        discounts(discountFilms) shouldBe listOf(3.15, 4.0, 7.5, 2.0)
    }

    "TODO 2.7.6 - it should use pattern matching with lists" {
        val film1 = Film("Videodrome", 1984, RandomDirector, listOf(Horror), 9.0)
        val film2 = Film("Donnie Darko", 2002, Kurosawa, listOf(Drama, SciFi, Thriller), 10.0)
        val film3 = Film("The Adventures of Baron Munchausen", 1989, RandomDirector, listOf(Comedy), 15.0)
        val film4 = Film("Brazil", 1985, RandomDirector, listOf(Action), 2.0)

        labelizeFilm(film1) shouldBe listOf("Horror - Videodrome")
        labelizeFilm(film2) shouldBe listOf(":( Donnie Darko", "Donnie Darko", "Thriller -> Kurosawa")
        labelizeFilm(film3) shouldBe listOf("The Adventures of Baron Munchausen")
        labelizeFilm(film4) shouldBe listOf("Action - Brazil")
    }
})
