import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.compose") version "1.0.0"
}

group = "me.sedra"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(files("src/main/resources/java/opencv-455.jar"))
    
    implementation("org.bytedeco:javacv-platform:1.3.1")
//    implementation(group = "org.bytedeco", name = "javacv", version = "0.9")
//    implementation(
//        group = "org.bytedeco.javacpp-presets",
//        name = "opencv",
//        version = "2.4.9-0.9",
//        classifier = "android-arm"
//    )
//    implementation(
//        group = "org.bytedeco.javacpp-presets",
//        name = "ffmpeg",
//        version = "2.3-0.9",
//        classifier = "android-arm"
//    )



}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MultiMediaVideo"
            packageVersion = "1.0.0"
        }
    }
}