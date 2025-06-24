plugins {
    alias(libs.plugins.android.application) // Plugin de aplicação Android
    alias(libs.plugins.kotlin.android)      // Suporte a Kotlin para Android
    alias(libs.plugins.kotlin.compose)      // Suporte para Jetpack Compose
    id("org.jetbrains.kotlin.kapt")         // Necessário para geração de código (ex: Room)
}

android {
    namespace = "com.example.labdocopo" // Identificador do pacote base
    compileSdk = 35                     // Versão da SDK usada para compilar a app

    defaultConfig {
        applicationId = "com.example.labdocopo"           // ID único da aplicação
        minSdk = 24                                       // Mínima versão de Android suportada (7.0)
        targetSdk = 35                                    // Versão de Android alvo
        versionCode = 1                                   // Código da versão (para atualização)
        versionName = "1.0"                               // Nome da versão mostrado ao utilizador
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Testes
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Desativa minificação no modo release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Ficheiro de regras para ofuscação (se ativares)
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // Compatibilidade com Java 11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11" // Bytecode compatível com Java 11
    }

    buildFeatures {
        compose = true // Ativa suporte ao Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0" // Versão do compilador Compose
    }
}

dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.7") // Navegação com Compose

    // Bibliotecas essenciais AndroidX/Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // BOM para controlar versões
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ✅ ROOM: base de dados local
    implementation("androidx.room:room-runtime:2.6.1")   // Biblioteca principal Room
    implementation("androidx.room:room-ktx:2.6.1")        // Extensões Kotlin (coroutines)
    kapt("androidx.room:room-compiler:2.6.1")             // Processamento de anotações

    // Testes unitários e instrumentados
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Ferramentas para debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
