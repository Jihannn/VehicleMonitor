plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = BuildConfig.namespace
    compileSdk = BuildConfig.compileSdk

//    useLibrary("android.car")

    defaultConfig {
        applicationId = BuildConfig.applicationIdService
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

//        ndk {
//            abiFilters.addAll(arrayListOf("x86_64", "armeabi-v7a", "arm64-v8a"))
//        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(mapOf("path" to ":lib_common")))
//    implementation(Others.retrofit)
//    implementation(AndroidX.carOld)
//    implementation(AndroidX.car)
//    implementation(AndroidX.carProjected)
//    implementation(AndroidX.carAutomotive)
}