# Kotlin Hands On


## Variables
```kotlin
val host: String = "https://xebia.fr"

val port: Int = 443

// shorter, since Type is inferred
val port = 443
```

## Functions
```kotlin
fun helloWorld(): String = "Hello World"

fun hello(name: String): String = "Hello $name"

fun helloWorld(): String {
    return "Hello World"
}

// side-effect function
fun helloWorld(): Unit {
    println("Hello World")
}

// shorter, since Unit return type can (almost always) be ommited
fun helloWorld() {
    println("Hello World")
}
```

## Lambdas
```kotlin
val product: (Int, Int) -> Int = { a, b -> a * b }

val pair: (Int) -> Boolean = { it -> it % 2 == 0 }

// shorter, with implicit parameter name
val pair: (Int) -> Boolean = { it % 2 == 0 }
```

## Classes
```kotlin
class User(val name: String) {
    fun greet() = "My name is $name"
}

interface Vehicle

class JetPack : Vehicle

class Plane : Vehicle {
    // ...
}
```

