plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.coffeeshopapp"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.coffeeshopapp"
        minSdk = 26
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("com.airbnb.android:lottie:6.0.0")
    implementation("com.github.LottieFiles:dotlottie-android:0.4.1")

    implementation ("com.github.Dimezis:BlurView:version-1.6.6")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.razorpay:checkout:1.6.26")  // Latest SDK version
    implementation ("com.google.code.gson:gson:2.8.9")

    implementation ("com.google.android.gms:play-services-auth:20.2.0")

    // For Google Authentication
    implementation("com.google.android.libraries.identity.googleid:googleid:1.0.0")

    // Firebase BOM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))

    // Firestore
    implementation ("com.google.firebase:firebase-firestore-ktx")

    // Firebase Authentication (if not added already)
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")  // If needed




}