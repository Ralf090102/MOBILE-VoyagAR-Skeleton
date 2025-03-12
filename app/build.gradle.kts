plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mobile_voyagar_skeleton"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobile_voyagar_skeleton"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Add SDL-specific configuration
        externalNativeBuild {
            cmake {
                arguments("-DANDROID_STL=c++_shared")
                cppFlags("-std=c++17")
            }
        }

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86")
            abiFilters.add("x86_64")
        }
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
    externalNativeBuild {
        cmake {
            path = file("jni/voyagAR/CMakeLists.txt")
            version = "3.22.1"

        }
    }
    buildFeatures {
        viewBinding = true
        prefab = true
    }

    // Add SDL assets to your sourceSets
    sourceSets {
        getByName("main") {
            assets.srcDirs(listOf("src/main/assets", "app/SDL/android-project/app/src/main/assets"))
            jniLibs.srcDirs(listOf("app/SDL/lib"))
            java.srcDirs(listOf("src/main/java", "app/SDL/android-project/app/src/main/java"))
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // We've removed the SDL AAR dependency since we're using the source directly
    // implementation(files("jni/sdl/libs/SDL3-3.2.8.aar"))
}