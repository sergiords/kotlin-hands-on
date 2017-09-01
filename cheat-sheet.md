# Kotlin Hands On


## Variables
```kotlin
val host: String = "https://xebia.fr"

val port: Int = 443

// shorter, since Type is inferred
val port = 443
```

## Control flow
```kotlin
val result = if (bob == alice) "same" else "different"

for (user in users) { ... }

val result = when (x) {
    1 -> "positive"
    0 -> "null"
    -1 -> "negative"
    else -> "unknown"
}

val result = when {
    x > 0 -> "positive"
    x == 0 -> "null"
    else -> "negative"
}
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

