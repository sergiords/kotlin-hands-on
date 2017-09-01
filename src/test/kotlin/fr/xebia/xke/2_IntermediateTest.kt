package fr.xebia.xke

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.specs.StringSpec

class DataClassTest : StringSpec({

    val john = SpecialDirector(
        name = "John Carpenter",
        bio = "was born in Carthage, New York")

    "it should provide 'toString'" {
        john.toString() shouldBe "SpecialDirector(name=John Carpenter, bio=was born in Carthage, New York, filmography=[])"
    }

    "it should provide 'copy'" {
        val updatedJohn = john.copy(name = "Mel Brooks")
        updatedJohn.bio shouldBe john.bio
    }

    "it should provide 'equals' " {
        val anotherJohn = SpecialDirector(
            name = "John Carpenter",
            bio = "was born in Carthage, New York",
            filmography = emptyList())
        anotherJohn shouldBe john
    }

})

class CompanionObjectTest : StringSpec({

    "it should be able to use companion objects" {
        val csvReader = FileFilmLoader.build("file.csv") { it shouldBe "Contents of FILE.CSV" }
        csvReader.actualPath shouldBe "FILE.CSV"

        val xmlReader = FileFilmLoader.build("file.xml") { it shouldBe "Contents of FILE.XML" }
        xmlReader.actualPath shouldBe "FILE.XML"
    }

})

class SealedClassTest : StringSpec({

    "${Genre::class.simpleName} should be sealed" {
        Genre::class.isSealed shouldBe true
    }

    "${Director::class.simpleName} should be sealed" {
        Director::class.isSealed shouldBe true
    }

    mapOf(
        Kurosawa to 1910,
        Hitchcock to 1899,
        Spielberg to 1946
    ).forEach {
        "${it.key.name} was born in year ${it.value}" {
            directorYearOfBirth(it.key) shouldBe it.value
        }
    }

})

class GettersSettersTest : StringSpec({

    val withMyFilms = listOf(
        Film("The Color Purple", 1985, Spielberg, listOf(Drama), 9.0),
        Film("Jurassic Park", 1993, Spielberg, listOf(SciFi), 10.0)
    )

    "${FilmCollection::has80sFilms} should call getter and return no film for default startingYear" {
        val filmCollection = FilmCollection(withMyFilms)
        filmCollection.has80sFilms shouldBe false
    }

    "${FilmCollection::has80sFilms} should call return 80's film for starting year 1980" {
        val filmCollection = FilmCollection(withMyFilms)
        filmCollection.periodStartYear = 1980
        filmCollection.has80sFilms shouldBe true
    }

    "${StringMagicBox::reversed} should have custom getter and setter" {
        val stringMagicBox = StringMagicBox()
        stringMagicBox.reversed shouldBe "eulav emoS"
        stringMagicBox.reversed = "reversed"
        stringMagicBox.reversed shouldBe "desrever"
    }

})

class GenericsVarianceTest : StringSpec({

    val sender = object : Sender<Mail> {
        override fun send(): Mail = throw NotImplementedException("NotCalled")
    }

    val receiver = object : Receiver<Text> {
        override fun receive(t: Text) = throw NotImplementedException("NotCalled")
    }

    "${::textSender.name} should return its parameter since parameter type and return type are covariant" {
        textSender(sender) shouldBe sender
    }

    "${::textReceiver.name} should return its parameter since parameter type and return type are contravariant" {
        textReceiver(receiver) shouldBe receiver
    }

    "useSiteVariance() should copy arrayOut[3] to arrayIn[5]" {
        val arrayIn = arrayOf(0, 2, 4, 6, 8, 10)
        val arrayOut = arrayOf(1, 3, 5, 7, 9, 11)

        useSiteVariance(arrayIn, arrayOut)

        arrayIn[5] shouldBe 7
        arrayOut[3] shouldBe 7
    }

})

class TypeAliases : StringSpec({

    "Create a type aliases corresponding to a dictionary" {
        (dict is Dictionary) shouldBe true
        dict.getOrDefault("hello", "unknown") shouldBe "used as a greeting or to begin a telephone conversation"
    }
})

class CollectionsTest : StringSpec({

    val ran = Film("Ran", 1985, Kurosawa, listOf(Action, Drama, War), 2.3)
    val rashomon = Film("Rashomon", 1950, Kurosawa, listOf(Crime, Drama), 3.0)
    val psyco = Film("Psycho", 1960, Hitchcock, listOf(Horror, Mistery, Thriller), 2.7)
    val vertigo = Film("Vertigo", 1958, Hitchcock, listOf(Mistery, Romance, Thriller), 5.1)

    val completeList = listOf(ran, rashomon, psyco, vertigo)
    val hitchcockFilms = listOf(psyco, vertigo)
    val kurosawaFilms = listOf(ran, rashomon)

    "it should use List#filter" {
        filmsMadeBy(Hitchcock, completeList) shouldBe hitchcockFilms
    }

    "it should use films List#filter and high order functions" {
        filmsMatchingFilter(completeList, { it.director == Hitchcock }) shouldBe hitchcockFilms
        filmsMatchingFilter(completeList, { it.director == Kurosawa }) shouldBe kurosawaFilms
    }

    "it should use folding to sum film prices" {
        sumPricesWithFolding(hitchcockFilms) shouldBe 7.8
        sumPricesWithFolding(kurosawaFilms) shouldBe 5.3
    }

})

class CollectionsInitializationTest : StringSpec({

    "${::films.name} should contain at least two films from Spielberg and have a genre defined" {
        (films.size >= 2) shouldBe true
        films.map { it.director }.distinct() shouldBe listOf(Spielberg)
        films.forEach { it.type.isNotEmpty() shouldBe true }
    }

    "${::filmsByYear.name} should contain at least two entries, each with at least one movie" {
        (filmsByYear.size >= 2) shouldBe true
        filmsByYear.forEach { it.value.isNotEmpty() shouldBe true }
    }

})

class SequenceTest : StringSpec({

    var mapCount = 0
    val mapWatch: (Int) -> Int = { it ->
        mapCount++ // this counter is to test that map is executed just twice
        it * it
    }

    "${::filterSeq.name}() should return true and only be executed twice" {
        filterSeq(mapWatch) shouldBe true
        mapCount shouldBe 2
    }

})

class PairTest : StringSpec({

    val expected = Pair(Pair("Yin", "Dark side"), Pair("Yang", "Bright side"))
    "${::mapPhilosophies.name}() should return $expected" {
        mapPhilosophies() shouldBe expected
    }

})

class LazyTest : StringSpec({

    "${::lazyInc.name} should be incremented only once and ${::lazyValue.name} should be computed after being accessed" {
        lazyInc shouldEqual 0
        lazyValue
        lazyValue
        lazyValue
        lazyInc shouldEqual 1
    }
})
