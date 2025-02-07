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
                implementation("com.google.mlkit:vision-common:17.3.0")
                implementation("androidx.camera:camera-core:1.4.1")

                if (project.findProperty("enableBarcodeScanning") == "true") {
                    implementation("com.google.android.gms:play-services-code-scanner:16.1.0")
                    implementation("com.google.mlkit:barcode-scanning:17.3.0")
                }
                if (project.findProperty("enableFaceDetection") == "true") {
                    implementation("com.google.mlkit:face-mesh-detection:16.0.0-beta3")
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
                if (project.findProperty("enableSmartReply") == "true") {
                    implementation("com.google.mlkit:smart-reply:17.0.4")
                }
                if (project.findProperty("enableLanguageDetection") == "true") {
                    implementation("com.google.mlkit:language-id:17.0.6")
                }
                if (project.findProperty("enableLanguageTranslation") == "true") {
                    implementation("com.google.mlkit:translate:17.0.3")
                }
                if (project.findProperty("enableTextExtraction") == "true") {
                    implementation("com.google.mlkit:entity-extraction:16.0.0-beta5")
                }

                if (project.findProperty("enablePoseDetection") == "true") {
                    implementation("com.google.mlkit:pose-detection:18.0.0-beta5")
                    implementation("com.google.mlkit:pose-detection-accurate:18.0.0-beta5")
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
            artifactId = "NeuralKMP",
            version = "0.0.3"
        )

        // Configure POM metadata for the published artifact
        pom {
            name.set("NeuralKMP")
            description.set("A kotlin multiplatform library that handles anything AI & MachineLearning. Supports iOS, Android & JVM")
            inceptionYear.set("2025")
            url.set("https://github.com/TheArchitect123/NeuralKMP")

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
                url.set("https://github.com/TheArchitect123/NeuralKMP")
            }
        }

        // Configure publishing to Maven Central
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

        // Enable GPG signing for all publications
        signAllPublications()
    }
}

signing {
    val privateKeyFile = project.properties["signing.privateKeyFile"] as? String
        ?: error("No Private key file found")
    val passphrase = project.properties["signing.password"] as? String
        ?: error("No Passphrase found for signing")

    // Read the private key from the file
    val privateKey = File(privateKeyFile).readText(Charsets.UTF_8)

    useInMemoryPgpKeys(privateKey, passphrase)
    sign(publishing.publications)
}

ksp {
    arg("moduleName", project.name)
}

android {
    namespace = "com.architect.neuralKmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}