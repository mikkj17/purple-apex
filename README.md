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

### WASM (Docker)

You can also build and run the WASM application as a Docker container:

```bash
docker build -t purple-apex-wasm .
docker run -p 8080:80 purple-apex-wasm
```

The application will then be available at [http://localhost:8080](http://localhost:8080).

*Note: The Docker build requires significant memory for WASM compilation. Ensure your Docker Desktop (or engine) is
configured with at least 10GB of RAM.*

## Project Structure

- `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for platform-specific code (e.g., `androidMain`, `iosMain`, `desktopMain`, `wasmJsMain`).
- `/iosApp` contains the iOS application entry point and SwiftUI code.

## Resources

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform Repository](https://github.com/JetBrains/compose-multiplatform)