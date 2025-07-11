plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") // ✅ Firebase services plugin
}

android {
    namespace = "com.example.babycare"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.babycare"
        minSdk = 24
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")


//    implementation(libs.androidx.drawerlayout)
    implementation(libs.material)

    // Firebase BoM - centralizes all Firebase versions
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")



    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    // Location services (used in Dashboard)
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Image Slider
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.0")

    // Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Lifecycle (LiveData, ViewModel)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // ✅ Glide for loading profile images
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.core:core-splashscreen:1.0.1")

}
