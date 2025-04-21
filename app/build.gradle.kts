import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
//    id("kotlin-kapt")
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
val networkIp = localProperties.getProperty("NETWORK_IP", "192.168.100.125") ?: "192.168.100.125"

val securityConfigFile = file("src/main/res/xml/network_security_config.xml")

if (securityConfigFile.exists()) {
    val updatedContent = securityConfigFile.readText().replace(
        Regex("<domain includeSubdomains=\"true\">.*?</domain>"),
        "<domain includeSubdomains=\"true\">$networkIp</domain>"
    )
    securityConfigFile.writeText(updatedContent)
}


android {
    namespace = "com.example.manage.laundry"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.manage.laundry"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(project.findProperty("KEYSTORE_FILE") as? String ?: "random.jks")
            storePassword = project.findProperty("KEYSTORE_PASSWORD") as? String ?: "random"
            keyAlias = project.findProperty("KEY_ALIAS") as? String ?: "random"
            keyPassword = project.findProperty("KEY_PASSWORD") as? String ?: "random"
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "IP_ADDRESS", "\"$networkIp\"")
            // Shop Owner
            buildConfigField("String", "DUMMY_SHOP_OWNER_EMAIL", "\"owner2@laundry.com\"")
            buildConfigField("String", "DUMMY_SHOP_OWNER_PASSWORD", "\"test\"")

            // Staff
            buildConfigField("String", "DUMMY_STAFF_EMAIL", "\"staff1@laundry.com\"")
            buildConfigField("String", "DUMMY_STAFF_PASSWORD", "\"test\"")

            // Customer
            buildConfigField("String", "DUMMY_CUSTOMER_EMAIL", "\"customer1@example.com\"")
            buildConfigField("String", "DUMMY_CUSTOMER_PASSWORD", "\"test\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "IP_ADDRESS", "\"$networkIp\"")

            // Shop Owner
            buildConfigField("String", "DUMMY_SHOP_OWNER_EMAIL", "\"\"")
            buildConfigField("String", "DUMMY_SHOP_OWNER_PASSWORD", "\"\"")

            // Staff
            buildConfigField("String", "DUMMY_STAFF_EMAIL", "\"\"")
            buildConfigField("String", "DUMMY_STAFF_PASSWORD", "\"\"")

            // Customer
            buildConfigField("String", "DUMMY_CUSTOMER_EMAIL", "\"\"")
            buildConfigField("String", "DUMMY_CUSTOMER_PASSWORD", "\"\"")
//            signingConfig = signingConfigs.getByName("debug")
            signingConfig = signingConfigs.getByName("release")
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
        buildConfig = true
    }

    flavorDimensions += "environment"

    productFlavors {
        create("mock") {
            dimension = "environment"
            applicationIdSuffix = ".mock"
            versionNameSuffix = "-mock"
            buildConfigField("Boolean", "USE_FAKE_VIEWMODEL", "true")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("Boolean", "USE_FAKE_VIEWMODEL", "false")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Ktor
    implementation(libs.bundles.ktor)

    // Coil3
    implementation(libs.bundles.coil)

    // Bottom Navigation
    implementation(libs.animated.navigation.bar)

    //Chart
    implementation(libs.compose.charts)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
}