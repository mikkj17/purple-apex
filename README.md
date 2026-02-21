# Purple Apex

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop, and WASM.

## Prerequisites

- **JDK 21** (Required for stable Kotlin Multiplatform/WASM compilation)
- Android SDK (for Android target)
- Xcode (for iOS target)

## Running the Application

### Desktop

```bash
./gradlew :composeApp:run
```

### WASM (Browser)

To run the WASM version in your browser:

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

*Note: Ensure you are using JDK 21. Newer JDK versions (like 25) may cause "IllegalArgumentException: 25" during
compilation.*

## Project Structure

- `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for platform-specific code (e.g., `androidMain`, `iosMain`, `desktopMain`, `wasmJsMain`).
- `/iosApp` contains the iOS application entry point and SwiftUI code.

## Resources

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform Repository](https://github.com/JetBrains/compose-multiplatform)