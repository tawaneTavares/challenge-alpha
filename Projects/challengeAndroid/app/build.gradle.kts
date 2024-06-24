import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.tcot.starwars"
    compileSdk = 34

    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.tcot.starwars"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            buildConfigField("String", "BASE_URL", "\"https://swapi.dev/\"")
            buildConfigField("String", "BASE_IMG_URL", "\"https://starwars-visualguide.com/assets/img/\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", "\"https://swapi.dev/\"")
            buildConfigField("String", "BASE_IMG_URL", "\"https://starwars-visualguide.com/assets/img/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding.isEnabled = true
}

tasks.getByPath("preBuild").dependsOn("ktlintFormat")

ktlint {
    android = true
    ignoreFailures = false
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Screen
    implementation(libs.paging)
    implementation(libs.coil)
    implementation(libs.splash)
    implementation(libs.androidx.navigation)

    // Viewmodel and Livedata
    implementation(libs.androidx.lifecycle)

    // Retrofit
    implementation(libs.moshi)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    // Coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)

    // hilt
    implementation(libs.androidx.hilt)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}
