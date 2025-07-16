import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms)
    alias(libs.plugins.firebase.crashlytics)
}

val keystoreProperties = Properties().apply {
    load(rootProject.file("keystore.properties").inputStream())
}

android {
    namespace = "dev.rhcehd123.dailylog"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.rhcehd123.dailylog"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.01"

        testInstrumentationRunner = "dev.rhcehd123.dailylog.hilt.HiltTestRunner"
        /*javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }*/
    }

    testOptions.unitTests.all {
        it.useJUnitPlatform()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.create("release").apply {
                storeFile = file(keystoreProperties["KEYSTORE_FILE"] as String)
                storePassword = keystoreProperties["KEYSTORE_PASSWORD"] as String
                keyAlias = keystoreProperties["KEY_ALIAS"] as String
                keyPassword = keystoreProperties["KEY_PASSWORD"] as String
            }
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
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.google.truth)
    testImplementation(libs.turbine)
    //kotest
    testImplementation(libs.kotest.assertion)
    testImplementation(libs.kotest.runner)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // datastore
    implementation(libs.androidx.datastore)

    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.tooling.preview)

    // room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // hilt
    implementation(libs.hilt.andriod)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // glance
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

    // calendar
    implementation(libs.kizitonwose.calendar.compose)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}
