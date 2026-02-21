# Stage 1: Build the application
FROM gradle:8.13-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

# Install libatomic which is required by the Node.js version used by Kotlin WASM tasks
USER root
RUN apt-get update && apt-get install -y libatomic1 && rm -rf /var/lib/apt/lists/*
USER gradle

# Build the WASM distribution
# Increased memory for both Gradle and Kotlin daemon to handle production compilation
ENV GRADLE_OPTS="-Dorg.gradle.jvmargs=-Xmx8g -Dkotlin.daemon.jvmargs=-Xmx8g -Dorg.gradle.workers.max=1"
RUN ./gradlew :composeApp:wasmJsBrowserDistribution --no-daemon

# Stage 2: Serve the static files with Nginx
FROM nginx:stable-alpine
# The output path for KMP WASM distribution usually follows this pattern:
COPY --from=build /home/gradle/project/composeApp/build/dist/wasmJs/productionExecutable /usr/share/nginx/html

# Expose port 80
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
