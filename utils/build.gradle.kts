import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "dev.jianastrero.utils"
version = "1.0.0"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlinx.reflect)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotest.jvm.junit)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.kotlinx.reflect)
            }
        }
    }
}

android {
    namespace = "dev.jianastrero.utils"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    testImplementation(libs.kotest.jvm.junit)
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    filter {
        isFailOnNoMatchingTests = false
    }
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "Jian Astrero's Utils"
        description = "A utility library for Kotlin Multiplatform projects"
        inceptionYear = "2023"
        url = "https://github.com/jianastrero/jianastrero-utils"
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "jianastrero"
                name = "Jian James Astrero"
                url = "https://github.com/jianastrero"
            }
        }
        scm {
            url = "https://github.com/jianastrero/jianastrero-utils"
            connection = "scm:git:git://github.com/jianastrero/jianastrero-utils.git"
            developerConnection = "scm:git:ssh://github.com/jianastrero/jianastrero-utils.git"
        }
    }
}
