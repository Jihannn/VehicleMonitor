plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
//    namespace巨坑。。。？？？会导致上层依赖模块找不到布局文件
//    namespace = BuildConfig.namespace
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    api(AndroidX.appcompat)
    api(AndroidX.core)
    api(AndroidX.activity)
    api(AndroidX.fragment)
    api(AndroidX.lifecycleLiveData)
    api(AndroidX.lifecycleViewModel)
    api(AndroidX.constraintlayout)
    api(Others.retrofit)
    api(Others.retrofitGsonConverter)
    api(Others.retrofitPersistentCookie)
    api(Others.material)
}