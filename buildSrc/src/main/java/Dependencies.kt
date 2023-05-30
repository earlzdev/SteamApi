import Versions.ioMockitoVersion
import Versions.mockitoKotlin
import Versions.mockitoVersion

object Versions {
    const val core = "1.7.0"
    const val appCompat = "1.6.1"
    const val material = "1.8.0"
    const val constraintLayout = "2.1.4"
//    const val junit = "4.13.2"
    const val junit = "4.12"
    const val androidJunit = "1.1.5"
    const val espresso = "3.5.1"
    const val dagger2 = "2.45"
    const val retrofit = "2.9.0"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val httpLoggingInterceptor = "4.9.0"
    const val room = "2.4.3"
    const val coroutines = "1.6.4"
    const val viewModel = "2.5.1"
    const val fragment = "1.5.4"
    const val lifecycleRuntime = "2.5.1"
    const val serialization = "1.4.1"
    const val roundImages = "2.3.0"
    const val glide = "4.15.0"
    const val cicerone = "7.1"
    const val navComponent = "2.5.3"
    const val workManagerVersion = "2.8.0"
    const val liveData = "2.6.1"
    const val mockitoVersion = "5.3.0"
    const val ioMockitoVersion = "1.4.1"
    const val mockitoKotlin = "4.1.0"
    const val coroutinesTest = "1.5.0"
}

object Dependencies {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junit}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockito = "org.mockito:mockito-core:$mockitoVersion"
    const val kotlinMockito = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlin"
    const val ioMockito = "io.mockk:mockk:$ioMockitoVersion"
    const val coroutinesTests = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
}

object Dagger2 {
    const val dagger = "com.google.dagger:dagger:${Versions.dagger2}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.httpLoggingInterceptor}"
    const val retrofitCoroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
}

object Room {
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomTest = "androidx.room:room-testing:${Versions.room}"
}

object LifecycleComponents {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
}

object RoundImages {
    const val roundImage = "com.makeramen:roundedimageview:${Versions.roundImages}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}:"
}

object Cicerone {
    const val cicerone = "com.github.terrakok:cicerone:${Versions.cicerone}"
}

object NavigationComponent {
    const val navComponentFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navComponent}"
    const val navComponentUi = "androidx.navigation:navigation-ui-ktx:${Versions.navComponent}"
}

object WorkManager {
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManagerVersion}"
}