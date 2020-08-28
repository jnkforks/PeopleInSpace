plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
    //id("com.juliozynger.floorplan")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
        }
    }
}

kotlin {
    targets {
        val sdkName: String? = System.getenv("SDK_NAME")

        val isiOSDevice = sdkName.orEmpty().startsWith("iphoneos")
        if (isiOSDevice) {
            iosArm64("iOS64")
        } else {
            iosX64("iOS")
        }

        val isWatchOSDevice = sdkName.orEmpty().startsWith("watchos")
        if (isWatchOSDevice) {
            watchosArm64("watch")
        } else {
            watchosX86("watch")
        }

        macosX64("macOS")
        android()
        jvm()
    }

    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
    }

    js {
        browser {
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}") {
                    isForce = true
                }

                // Ktor
                implementation("io.ktor:ktor-client-core:${Versions.ktor}")
                implementation("io.ktor:ktor-client-json:${Versions.ktor}")
                implementation("io.ktor:ktor-client-logging:${Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${Versions.ktor}")

                // Serialize
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}")

                // SQL Delight
                implementation("com.squareup.sqldelight:runtime:${Versions.sqlDelight}")
                implementation("com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}")

                // koin
                api("org.koin:koin-core:${Versions.koin}")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:${Versions.ktor}")

                // SQL Delight
                implementation("com.squareup.sqldelight:android-driver:${Versions.sqlDelight}")
                //implementation("com.squareup.sqldelight:coroutines-extensions-jvm:${Versions.sqlDelight}")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-apache:${Versions.ktor}")
                implementation(Ktor.slf4j)

                // SQL Delight
                implementation("org.xerial:sqlite-jdbc:${Versions.sqliteJdbcDriver}")
                implementation("com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}")
            }
        }

        val iOSMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:${Versions.ktor}")

                // SQL Delight
                implementation("com.squareup.sqldelight:native-driver:${Versions.sqlDelight}")
            }
        }

        val watchMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:${Versions.ktor}")

                // SQL Delight
                implementation("com.squareup.sqldelight:native-driver:${Versions.sqlDelight}")
            }
        }

        val macOSMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-cio:${Versions.ktor}")

                // SQL Delight
                implementation("com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}")
                //implementation("com.squareup.sqldelight:runtime-macosx64:${Versions.sqlDelight}")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${Versions.ktor}")

                // SQL Delight
                //implementation("com.squareup.sqldelight:sqljs-driver:${Versions.sqlDelight}")
                //implementation("com.squareup.sqldelight:runtime-js:${Versions.sqlDelight}")
            }
        }
    }
}

sqldelight {
    database("PeopleInSpaceDatabase") {
        packageName = "com.surrus.peopleinspace.db"
        sourceFolders = listOf("sqldelight")
        //schemaOutputDirectory = file("$projectDir/sqldelight-schemas")
    }
}

//floorPlan {
//    schemaLocation.value("$projectDir/sqldelight-schemas")
//    outputLocation.value("$projectDir/floorplan-output")
//    outputFormat {
//        svg {
//            enabled(true)
//        }
//    }
//}
