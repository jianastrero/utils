# Jian Astrero's Utils

A comprehensive utility library for Kotlin Multiplatform projects, providing a collection of useful functions and
classes for common development tasks.

## Overview

This library contains various utility functions developed and will primarily be used
by [Jian James Astrero](https://github.com/jianastrero) for use in development projects. The utilities are organized
into packages for different purposes such as logging, reflection, table formatting, and more.

## Features

### Logging Utilities

The library provides enhanced logging capabilities:

- **Simple Logging**: Log any object with `log()` extension function
- **Deep Logging**: Detailed object inspection with `logDeep()` extension function
- **Custom Log Levels**: DEBUG, INFO, WARNING, ERROR levels for filtering logs
- **Multiplatform Support**: Consistent logging across different platforms

```kotlin
// Basic logging
val user = User("John", 25)
user.log() // Logs: "(com.example.User) User(name=John, age=25)"

// Deep logging with property inspection
user.logDeep("currentUser") // Logs detailed information about the user object
```

### Table Formatting

Create formatted text tables for console output or text files:

```kotlin
val tableString = table(2) {
    header {
        cell("Name")
        cell("Age")
    }
    item {
        cell("John")
        cell("25")
    }
    item {
        cell("Jane")
        cell("30")
    }
}

// Output:
// ┌────────────┐
// │ Name │ Age │
// │────────────│
// │ John │ 25  │
// │ Jane │ 30  │
// └────────────┘
```

#### Concise Table Building with Operators

The library also provides operator overloads for more concise table building:

```kotlin
// Using operator overloads for headers and items
val tableString = table(2) {
    !arrayOf("Name", "Age")  // Add header cells using the not operator
    +arrayOf("John", "25")   // Add item cells using the unary plus operator
    +arrayOf("Jane", "30")
}

// Using operators for spanning multiple columns
val userInfoTable = table(3) {
    !("User Information" * 3)  // Header cell spanning 3 columns
    +arrayOf("John", "25", "Admin")
}

// Using operators within header and item blocks
val detailedTable = table(2) {
    header {
        +"Name"              // Add a cell using unary plus
        "Details" * 1        // Add a cell with span using times operator
    }
    item {
        +"John"
        +"Software Developer"
    }
}
```

### Reflection Utilities

Utilities for working with reflection across platforms:

- Get properties of objects
- Access class information
- Inspect call stacks

### Other Utilities

- **Number Extensions**: Useful extensions for numeric types
- **String Formatting**: Functions for formatting strings
- **UUID Generation**: Platform-independent UUID generation
- **Stack Trace Utilities**: Functions for working with stack traces

## Installation

Add the dependency to your `build.gradle.kts` file:

### Android

```kotlin
// For all variants
implementation("io.github.jianastrero:utils:1.0.0")

// For Debug Only
debugImplementation("io.github.jianastrero:utils:1.0.0")
```

### Other platforms

```kotlin
dependencies {
    implementation("io.github.jianastrero:utils:1.0.0")
}
```

## Usage Notes

- This library is primarily intended for development use
- Some features may be considered overkill for certain applications - use at your discretion
- _Unused functions will be automatically removed when compiling for release builds_

## License

MIT License

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

[Jian James Astrero](https://github.com/jianastrero)
