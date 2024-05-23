plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

apply(from = "${rootProject.projectDir}/publish.gradle")

android {

    namespace = "com.ramcosta.composedestinations.wear"
    compileSdk = libs.versions.compileSdk.get().toIntOrNull()

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles.add(File("consumer-rules.pro"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

kotlin {
    jvmToolchain(11)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += listOf(
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=com.ramcosta.composedestinations.annotation.internal.InternalDestinationsApi"
    )
}

dependencies {
    api(project(mapOf("path" to ":compose-destinations")))

    api(libs.wear.compose.navigation)
}
