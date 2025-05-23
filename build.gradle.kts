
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
}
// Add repositories for all sub-projects

// Define common variables (optional but recommended)
    val compileSdk by extra(34)
    val minSdk by extra(24)
    val targetSdk by extra(34)
    val versionCode by extra(1)
    val versionName by extra("1.0")

