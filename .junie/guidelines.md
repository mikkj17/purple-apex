### Build and Configuration

This project is a Kotlin Multiplatform (KMP) application targeting Android, iOS, and Desktop (JVM), using Compose
Multiplatform for the UI.

#### Prerequisites

- JDK 21
- Android SDK (for Android target)
- Xcode (for iOS target)

#### Development Workflow

The project supports **Compose Hot Reload**. When making changes, it's often not necessary to manually trigger a build,
as the running application will update automatically.

#### Key Configuration Files

- `build.gradle.kts` (Root): Plugin management.
- `composeApp/build.gradle.kts`: Main application configuration, including source sets for `commonMain`, `androidMain`,
  `desktopMain`, etc.
- `gradle/libs.versions.toml`: Centralized dependency management.

#### GraphQL Support

The project uses **Apollo GraphQL**.

- Schema and queries are located in `composeApp/src/commonMain/graphql`.
- Generated code is automatically managed by the Apollo plugin.
- Configure the endpoint in `composeApp/build.gradle.kts` under the `apollo` block.

---

### Testing Guidelines

The project uses `kotlin.test` for unit testing and Compose's `ui-test` for UI testing.

#### Running Tests

To run all tests across all platforms:

```bash
./gradlew test
```

To run tests for a specific platform:

- Android: `./gradlew testDebugUnitTest`
- Desktop: `./gradlew desktopTest`
- iOS: `./gradlew iosSimulatorArm64Test`

#### Adding New Tests

1. **Unit Tests**: Place in `composeApp/src/commonTest/kotlin`. Use `kotlin.test` annotations (`@Test`).
2. **UI Tests**: Also in `commonTest`. Use `runComposeUiTest` from `androidx.compose.ui.test`.
    - **Note**: UI tests currently might fail on the Android target if run in an environment without proper Robolectric
      setup (due to `android.os.Build.FINGERPRINT` being null), but they work on Desktop and iOS.

#### Example Test

Here is a simple unit test demonstrating the setup:

```kotlin
class SimpleVerificationTest {
    @Test
    fun testAddition() {
        assertEquals(4, 2 + 2)
    }
}
```

---

### Additional Development Information

#### Dependency Injection

**Koin** is used for dependency injection.

- ViewModels are injected using `koinViewModel()` in Composable functions.
- Multiplatform compatibility is maintained using `koin-compose` and `koin-compose-viewmodel`.

#### UI Framework

**Compose Multiplatform** is the primary UI framework.

- Core UI logic should reside in `commonMain`.
- Platform-specific UI can be implemented using `expect`/`actual` or by providing platform-specific dependencies.

#### Fuzzy Search

The project includes a custom fuzzy search implementation in `com.example.purpleapex.core.fuzzysearch`.

- It uses the **Jaro-Winkler** similarity algorithm.
- Useful for searching drivers, constructors, and circuits with typo tolerance.

#### Navigation

Jetbrains Compose Navigation (`libs.jetbrains.compose.navigation`) is used for handling app flow.

- Routes are defined in `com.example.purpleapex.app.Route`.
