import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    id("org.gradle.maven-publish")
    id("signing")
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.28.0"
    id("com.google.devtools.ksp")
}

repositories {
    mavenCentral() // For public libraries
    google() // For Android libraries
}

val ktorVersion = "3.0.0"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.thearchitect123:kmpEssentials:1.8.5")
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                if (project.findProperty("enableBarcodeScanning") == "true") {
                    implementation("com.google.mlkit:barcode-scanning:17.3.0")
                }
                if (project.findProperty("enableFaceDetection") == "true") {
                    implementation("com.google.mlkit:face-detection:16.1.7")
                }
                if (project.findProperty("enableTextRecognition") == "true") {
                    implementation("com.google.mlkit:text-recognition:16.0.1")
                }
                if (project.findProperty("enableSelfieSegmentation") == "true") {
                    implementation("com.google.mlkit:segmentation-selfie:16.0.0-beta6")
                }
                if (project.findProperty("enableVisionInterfaces") == "true") {
                    implementation("com.google.mlkit:vision-interfaces:16.3.0")
                }
                if (project.findProperty("enableImageLabeling") == "true") {
                    implementation("com.google.mlkit:image-labeling:17.0.9")
                }
                if (project.findProperty("enableObjectDetection") == "true") {
                    implementation("com.google.mlkit:object-detection:17.0.2")
                }
                if (project.findProperty("enableDigitalInkRecognition") == "true") {
                    implementation("com.google.mlkit:digital-ink-recognition:18.1.0")
                }
                if (project.findProperty("enableDocumentScanner") == "true") {
                    implementation("com.google.android.gms:play-services-mlkit-document-scanner:16.0.0-beta1")
                }
            }
        }

        // iOS Targets
        val iosArm64Main by getting
        val iosX64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {

            }
        }
    }
}


afterEvaluate {
    mavenPublishing {
        // Define coordinates for the published artifact
        coordinates(
            groupId = "io.github.thearchitect123",
            artifactId = "KmpMLKit",
            version = "0.0.1"
        )

        // Configure POM metadata for the published artifact
        pom {
            name.set("KmpMLKit")
            description.set("A kotlin multiplatform library that makes it easier to work with Google's MLKit features. Supports both iOS & Android")
            inceptionYear.set("2025")
            url.set("https://github.com/TheArchitect123/KmpMLKit")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }

            // Specify developers information
            developers {
                developer {
                    id.set("Dan Gerchcovich")
                    name.set("TheArchitect123")
                    email.set("dan.developer789@gmail.com")
                }
            }

            // Specify SCM information
            scm {
                url.set("https://github.com/TheArchitect123/KmpMLKit")
            }
        }

        // Configure publishing to Maven Central
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

        // Enable GPG signing for all publications
        signAllPublications()
    }
}

tasks.named("sourcesJar").configure { dependsOn(":shared:kspCommonMainKotlinMetadata") }

ksp {
    arg("moduleName", project.name)
}

android {
    namespace = "com.architect.kmpmlkit"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    buildFeatures{
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}