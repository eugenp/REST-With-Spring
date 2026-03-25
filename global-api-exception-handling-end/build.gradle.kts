plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.h2)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.webflux)
    testRuntimeOnly(libs.junit.platform.launcher)
}

group = "com.baeldung"
version = "2.0.0"
description = "global-api-exception-handling-end"
java.sourceCompatibility = JavaVersion.VERSION_21

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.test {
    useJUnitPlatform()
    exclude("**/*LiveTest.class")
}
