plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") // Required for Firebase
}

android {
    namespace = "com.example.pkm_2"
    compileSdk = 36   // ❗ FIX: remove broken "release(36)" line

    defaultConfig {
        applicationId = "com.example.pkm_2"
        minSdk = 24
        targetSdk = 36  // ✔ stable version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lottie
    implementation("com.airbnb.android:lottie:6.0.0")

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Firebase Auth
    implementation("com.google.firebase:firebase-auth")

    // Google Sign-In (IMPORTANT!)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

