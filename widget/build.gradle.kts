plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.widget"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.material3)

    //// Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    androidTestImplementation(libs.google.dagger.hilt.android.testing)
    kspAndroidTest(libs.google.dagger.hilt.android.testing)

    ////Room
    val roomVersion = "2.6.1"
    api("androidx.room:room-runtime:$roomVersion")
    // annotationProcessor("androidx.room:room-compiler:$roomVersion")
    // annotationProcessor("com.google.dagger:dagger-compiler:2.51.1")
    api("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation(project(":database"))
    implementation(project(":repository"))

    //wear os
    implementation(libs.play.services.wearable)

    //// Moshi
    implementation(libs.squareup.retrofit2.converter.moshi)
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.moshi.adapters)
    ksp(libs.squareup.moshi.kotlin.codegen)

    ////Widget
    // For AppWidgets support
    implementation( "androidx.glance:glance-appwidget:1.0.0" )
    // For interop APIs with Material 2
    implementation( "androidx.glance:glance-material:1.0.0" )
    // For interop APIs with Material 3
    implementation( "androidx.glance:glance-material3:1.0.0" )

    testImplementation(libs.robolectric)
}