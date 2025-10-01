plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.kilkbid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kilkbid"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    implementation(libs.room.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room runtime
    implementation ("androidx.room:room-runtime:2.5.2")

    // For Java projects: annotation processor
    annotationProcessor ("androidx.room:room-compiler:2.5.2")

    implementation ("androidx.cardview:cardview:1.0.0")

    // Google Sign-In
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    // Facebook Login
    implementation ("com.facebook.android:facebook-login:16.3.0")
}