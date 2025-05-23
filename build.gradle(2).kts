plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
android {
    namespace = "com.yourcompany.shoppinglist"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.yourcompany.shoppinglist"
        minSdk = (rootProject.extra["minSdk"] as Int)
        targetSdk = (rootProject.extra["targetSdk"] as Int)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.16.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.activity:activity-compose:1.8.2")

    // Jetpack Compose
    implementation (platform("androidx.compose:compose-bom:2023.10.01"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material:material")
    implementation ("androidx.compose.material:material-icons-extended")

    // Room Database
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
}